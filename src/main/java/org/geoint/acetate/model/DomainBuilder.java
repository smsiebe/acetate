/*
 * Copyright 2016 geoint.org.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.geoint.acetate.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import org.geoint.acetate.functional.ThrowingFunction;
import org.geoint.acetate.serialization.SerializationFormat;
import org.geoint.acetate.serialization.TypeCodec;
import org.geoint.acetate.serialization.TypeDeserializer;
import org.geoint.acetate.serialization.TypeSerializer;
import org.geoint.acetate.serialization.ValueCodec;

/**
 * Fluid interface used define a domain model.
 * <p>
 * A DomainBuilder instance becomes invalid after {@link DomainBuilder#build() }
 * is called, no further methods can be called on the builder except 
 * {@link DomainBuilder#build() }, which will return the same DomainModel
 * instance.
 * <p>
 * DomainBuilder instances should be expected to not be thread-safe.
 *
 * @author steve_siebert
 */
public class DomainBuilder {

    private final String namespace;
    private final String version;
    private String description = null;
    private final NamedSet<TypeBuilder> typeBuilders = new NamedSet<>(TypeBuilder::getTypeName);
    private DomainModel model = null; //if not null the builder is no longer valid
    private TypeResolver resolver; //may be null

    public DomainBuilder(String namespace, String version) {
        this.namespace = namespace;
        this.version = version;
    }

    public DomainBuilder(String namespace, String version,
            TypeResolver resolver) {
        this.namespace = namespace;
        this.version = version;
        this.resolver = resolver;
    }

    public DomainBuilder withDescription(String domainModelDescription)
            throws IllegalStateException {
        verifyBuilderState();
        this.description = domainModelDescription;
        return this;
    }

    public ValueBuilder defineValue(String name)
            throws InvalidModelException, IllegalStateException {
        return new ValueBuilder(this::defineType, name);
    }

    public ValueBuilder defineValue(String name,
            TypeCodec defaultCharCodec, TypeCodec defaultBinCodec)
            throws InvalidModelException, IllegalStateException {
        return new ValueBuilder(this::defineType, name)
                .withDefaultCharCodec(defaultCharCodec.getClass())
                .withDefaultBinCodec(defaultBinCodec.getClass());
    }

    public ValueBuilder defineValue(String name, String desc) {
        return new ValueBuilder(this::defineType,
                name, desc);
    }

    public ValueBuilder defineValue(String name, String desc,
            TypeCodec defaultCharCodec, TypeCodec defaultBinCodec)
            throws InvalidModelException, IllegalStateException {
        return new ValueBuilder(this::defineType, name)
                .withDefaultBinCodec(defaultBinCodec.getClass())
                .withDefaultBinCodec(defaultBinCodec.getClass());
    }

    public EventBuilder defineEvent(String typeName) {
        return new EventBuilder(this::defineType, typeName);
    }

    public EventBuilder defineEvent(String typeName, String desc) {
        return new EventBuilder(this::defineType, typeName)
                .withDescription(desc);
    }

    /**
     * Method reference {@link ThrowingFunction callback} that is called when a {@link TypeBuilder#build()
     * } method is called, registering the type builder with the domain builder
     * and returning the domain builder (this keeps the API quite fluid).
     *
     * @param type domain type builder
     * @return this builder
     * @throws InvalidModelException if the type
     */
    private DomainBuilder defineType(TypeBuilder type)
            throws InvalidModelException {
        verifyBuilderState();
        if (!typeBuilders.add(type)) {
            throw new DuplicateNamedTypeException(type.typeName);
        }
        return this;
    }

    public DomainModel build() throws InvalidModelException {

        if (model == null) {

            /*
             * types are resolved in the following sequence, returning the first
             * resolution found:
             *  1) if the type is for this domain, check the collection of 
             *     types that have already been created during this build process
             *  2) if a TypeResolver was provided (constructor), attempt to 
             *     resolve from it
             *  3) if the type is for this domain, attempt to resolve the type 
             *     from any remaining domain type definitions 
             *  4) throw UnknownTypeException
             */
            CollectionTypeResolver builtResolver = new CollectionTypeResolver();
            HierarchicalTypeResolver resolvers = HierarchicalTypeResolver.newHierarchy(builtResolver);
            resolvers.addChild(resolver);
            resolvers.addChild(new BuilderTypeResolver(resolvers));

            return DomainModel.newInstance(namespace, version, description,
                    typeBuilders.toCollection(
                            builtResolver::getTypes, //created types are put into our buildResolver collection
                            (t) -> (DomainType) t.createModel(resolvers)));
        }
        return model;
    }

    private class BuilderTypeResolver implements TypeResolver {

        private final TypeResolver resolver;

        public BuilderTypeResolver(TypeResolver resolver) {
            this.resolver = resolver;
        }

        @Override
        public Optional<DomainType> findType(String ns,
                String v, String tn) {
            Optional<TypeBuilder> typeBuilder = typeBuilders.stream()
                    .filter((b) -> namespace.contentEquals(ns))
                    .filter((b) -> version.contentEquals(v))
                    .filter((b) -> b.typeName.contentEquals(tn))
                    .findFirst();

            if (typeBuilder.isPresent()) {
                try {
                    return Optional.of((DomainType) typeBuilder.get()
                            .createModel(resolver));
                } catch (InvalidModelException ex) {
                }
            }
            return Optional.empty();
        }

    }

    /**
     * Simple in-memory type resolver.
     * <p>
     * This class is not thread-safe, however a concurrent collection may be
     * provided for thread-safe access.
     */
    private class CollectionTypeResolver implements TypeResolver {

        private Collection<DomainType> types;

        public CollectionTypeResolver() {
            types = new ArrayList<>();
        }

        public CollectionTypeResolver(Collection<DomainType> types) {
            this.types = types;
        }

        /**
         * Returns the types known to the resolver instance.
         * <p>
         * This method returns the actual collection from wit the resolver.
         *
         * @return resolved types
         */
        public Collection<DomainType> getTypes() {
            return types;
        }

        @Override
        public Optional<DomainType> findType(String namespace, String version,
                String typeName) {
            return types.stream()
                    .filter((t) -> t.getNamespace().contentEquals(namespace))
                    .filter((t) -> t.getVersion().contentEquals(version))
                    .filter((t) -> t.getName().contentEquals(typeName))
                    .findFirst();
        }

    }

    private void verifyBuilderState() throws IllegalStateException {
        if (model != null) {
            throw new IllegalStateException("DomainBuilder has been built "
                    + "and can no longer be modified.");
        }
    }

    private void verifyLocalType(DomainType type) throws InvalidModelException {

        if (!type.getNamespace().contentEquals(namespace)) {
            throw new InvalidModelException(
                    String.format("Cannot add type '%s' to domain "
                            + "'%s-%s', namespace does not match.",
                            type.toString(), namespace, version));
        }

        if (!type.getVersion().contentEquals(version)) {
            throw new InvalidModelException(
                    String.format("Cannot add type '%s' to domain '%s-%s', "
                            + "domain version does not match.",
                            type.toString(), namespace, version));
        }
    }

    public abstract class ModelBuilder<M> {

        /**
         * Creates the domain model component from this builder.
         *
         * @param resolver domain type resolver that may be used to resolve
         * references
         * @return domain model
         * @throws InvalidModelException if the type definition is not valid
         */
        protected abstract M createModel(TypeResolver resolver)
                throws InvalidModelException;
    }

    public abstract class TypeBuilder<T extends DomainType, B extends TypeBuilder>
            extends ModelBuilder<T> {

        final ThrowingFunction<B, DomainBuilder, InvalidModelException> onBuild;
        final String typeName;
        String description;

        public TypeBuilder(
                ThrowingFunction<B, DomainBuilder, InvalidModelException> onBuild,
                String typeName) {
            this(onBuild, typeName, null);
        }

        public TypeBuilder(
                ThrowingFunction<B, DomainBuilder, InvalidModelException> onBuild,
                String typeName, String description) {
            this.onBuild = onBuild;
            this.typeName = typeName;
            this.description = description;
        }

        String getTypeName() {
            return typeName;
        }

        public B withDescription(String desc) {
            this.description = desc;
            return getBuilder();
        }

        /**
         * Completes the domain type definition, returning the DomainBuilder for
         * which this type is defined.
         *
         * @return domain model builder (not this builder)
         * @throws InvalidModelException if the event is invalid
         */
        public final DomainBuilder build() throws InvalidModelException {
            return onBuild.apply(getBuilder());
        }

        protected B getBuilder() {
            return (B) this;
        }

    }

    public class EventBuilder extends ComposedTypeBuilder<EventType, EventBuilder> {

        public EventBuilder(ThrowingFunction<EventBuilder, DomainBuilder, InvalidModelException> onBuild,
                String typeName) {
            super(onBuild, typeName);
        }

        public EventBuilder(ThrowingFunction<EventBuilder, DomainBuilder, InvalidModelException> onBuild,
                String typeName, String description) {
            super(onBuild, typeName, description);
        }

        @Override
        protected EventType createModel(TypeResolver resolver)
                throws InvalidModelException {
            return new EventType(namespace, this.typeName, version,
                    this.description, getCompositeRefs(resolver));
        }

    }

    public class OperationBuilder extends ModelBuilder<ResourceOperation> {

        private final ResourceBuilder resource;
        private final String operationName;
        private String description;
        private boolean idempotent = false;
        private boolean safe = false;
        private NamedSet<NamedTypeRefBuilder> parameters;
        private NamedTypeRefBuilder returnEvent;

        public OperationBuilder(ResourceBuilder rb, String operationName) {
            this.resource = rb;
            this.operationName = operationName;
        }

        public OperationBuilder withDescription(String desc) {
            this.description = desc;
            return this;
        }

        public OperationBuilder safe() {
            this.safe = true;
            return this;
        }

        public OperationBuilder notSafe() {
            this.safe = false;
            return this;
        }

        public OperationBuilder idempotent() {
            this.idempotent = true;
            return this;
        }

        public OperationBuilder notIdempotent() {
            this.idempotent = false;
            return this;
        }

        public NamedTypeRefBuilder<OperationBuilder> withParameter(
                String paramName, String domainTypeName)
                throws InvalidModelException {
            return withParameter(paramName, namespace, version, domainTypeName);
        }

        public NamedTypeRefBuilder<OperationBuilder> withParameter(String paramName,
                String paramNamespace, String paramVersion, String paramTypeName)
                throws InvalidModelException {
            NamedTypeRefBuilder<OperationBuilder> ref
                    = new NamedTypeRefBuilder(this, paramName,
                            paramNamespace, paramVersion, paramTypeName);

            if (!parameters.add(ref)) {
                throw new DuplicateNamedTypeException(paramName);
            }
            return ref;
        }

        public NamedTypeRefBuilder<OperationBuilder> createsEvent(
                String refName, String localDomainTypeName)
                throws InvalidModelException {
            return createsEvent(refName, namespace, version, localDomainTypeName);
        }

        public NamedTypeRefBuilder<OperationBuilder> createsEvent(
                String refName, String eventNamespace, String eventVersion,
                String eventDomainTypeName) throws InvalidModelException {
            this.returnEvent = new NamedTypeRefBuilder(this, refName,
                    eventNamespace, eventVersion, eventDomainTypeName);
            return this.returnEvent;
        }

        public ResourceBuilder build() {
            return resource;
        }

        @Override
        protected ResourceOperation createModel(TypeResolver resolver)
                throws InvalidModelException {
            return new ResourceOperation(namespace, version,
                    this.resource.typeName, operationName, description,
                    idempotent, safe,
                    parameters.toList((r) -> r.createModel(resolver)),
                    returnEvent.createModel(resolver));
        }

    }

    public class ResourceBuilder extends ComposedTypeBuilder<ResourceType, ResourceBuilder> {

        final NamedSet<NamedTypeRefBuilder> links
                = new NamedSet<>(NamedTypeRefBuilder::getRefName);
        final NamedSet<OperationBuilder> operations
                = new NamedSet<>((o) -> o.operationName);

        public ResourceBuilder(ThrowingFunction<ResourceBuilder, DomainBuilder, InvalidModelException> onBuild,
                String typeName) {
            super(onBuild, typeName);
        }

        public ResourceBuilder(ThrowingFunction<ResourceBuilder, DomainBuilder, InvalidModelException> onBuild,
                String typeName, String description) {
            super(onBuild, typeName, description);
        }

        public OperationBuilder withOperation(String opName) {
            OperationBuilder op = new OperationBuilder(this, opName);
            operations.add(op);
            return op;
        }

        /**
         * Defines local (same domain) linked resource type.
         *
         * @param refName local name for this linked resource
         * @param localTypeName domain type name of the resource
         * @throws InvalidModelException if invalid definition
         * @return this builder (fluid interface)
         */
        public NamedTypeRefBuilder<ResourceBuilder> withLink(String refName,
                String localTypeName)
                throws InvalidModelException {
            return withLink(refName, namespace, version,
                    localTypeName);
        }

        /**
         * Defines a ResourceType (same or external domain) as a resource link.
         *
         * @param refName local name for this linked resource
         * @param compositeNamespace namespace of the link type
         * @param compositeVersion verison of the link type
         * @param compositeName type name of the link type
         * @throws InvalidModelException if invalid definition
         * @return this builder (fluid interface)
         */
        public NamedTypeRefBuilder<ResourceBuilder> withLink(String refName,
                String compositeNamespace, String compositeVersion,
                String compositeName) throws InvalidModelException {

            NamedTypeRefBuilder<ResourceBuilder> ref = new NamedTypeRefBuilder(this, refName,
                    compositeNamespace, compositeVersion, compositeName);

            if (!composites.add(ref)) {
                throw new DuplicateNamedTypeException(refName);
            }
            return ref;
        }

        @Override
        protected ResourceType createModel(TypeResolver resolver)
                throws InvalidModelException {

            return new ResourceType(namespace, typeName, version, description,
                    getCompositeRefs(resolver),
                    links.toList((l) -> {
                        NamedTypeRef ref = l.createModel(resolver);
                        if (!ResourceType.class.isAssignableFrom(
                                ref.getReferencedType().getClass())) {
                            throw new InvalidModelException(
                                    String.format("Resource links can only be a domain "
                                            + "ResourceType, refererence '%s' but was "
                                            + "found to be of type '%s'",
                                            ref.toString(),
                                            ref.getReferencedType().getClass().getName()));
                        }
                        return ref;
                    }),
                    operations.toList((o) -> o.createModel(resolver)));
        }
    }

    public class ValueBuilder extends TypeBuilder<ValueType, ValueBuilder> {

        private TypeCodec defaultCharCodec;
        private TypeCodec defaultBinCodec;

        public ValueBuilder(
                ThrowingFunction<ValueBuilder, DomainBuilder, InvalidModelException> onBuild,
                String typeName) {
            super(onBuild, typeName);
        }

        public ValueBuilder(
                ThrowingFunction<ValueBuilder, DomainBuilder, InvalidModelException> onBuild,
                String typeName, String description) {
            super(onBuild, typeName, description);
        }

        public ValueBuilder withDefaultCharCodec(
                SerializationFormat format, TypeSerializer serializer,
                TypeDeserializer deserializer) {
            this.defaultCharCodec = ValueCodec.valueCodec(namespace, version,
                    typeName, format, serializer, deserializer);
            return this;
        }

        public ValueBuilder withDefaultCharCodec(
                Class<? extends TypeCodec> codecClass)
                throws InvalidModelException {
            this.defaultCharCodec = loadCodec(codecClass);
            return this;
        }

        public ValueBuilder withDefaultBinCodec(
                SerializationFormat format,
                TypeSerializer serializer, TypeDeserializer deserializer) {
            this.defaultBinCodec = ValueCodec.valueCodec(namespace, version,
                    typeName, format, serializer, deserializer);
            return this;
        }

        public ValueBuilder withDefaultBinCodec(
                Class<? extends TypeCodec> codecClass)
                throws InvalidModelException {
            this.defaultBinCodec = loadCodec(codecClass);
            return this;
        }

        @Override
        protected ValueType createModel(TypeResolver resolver)
                throws InvalidModelException {
            return new ValueType(namespace, typeName, version, description,
                    defaultCharCodec, defaultBinCodec);
        }

        private TypeCodec loadCodec(Class<? extends TypeCodec> codecClass)
                throws InvalidModelException {
            try {
                return codecClass.newInstance();
            } catch (InstantiationException | IllegalAccessException ex) {
                throw new InvalidModelException(String.format("Unable to "
                        + "instantiate value codec class '%s' using no-arg "
                        + "constructor", codecClass.getName()));
            }
        }
    }

    public abstract class ComposedTypeBuilder<T extends DomainType, B extends TypeBuilder>
            extends TypeBuilder<T, B> {

        final NamedSet<NamedTypeRefBuilder> composites
                = new NamedSet<>(NamedTypeRefBuilder::getRefName);

        public ComposedTypeBuilder(
                ThrowingFunction<B, DomainBuilder, InvalidModelException> onBuild,
                String typeName) {
            super(onBuild, typeName);
        }

        public ComposedTypeBuilder(
                ThrowingFunction<B, DomainBuilder, InvalidModelException> onBuild,
                String typeName, String description) {
            super(onBuild, typeName, description);
        }

        /**
         * Defines local (same domain) ValueType as a composite of the event.
         *
         * @param refName name for this composite type
         * @param localTypeName value type name fro
         * @throws InvalidModelException if this definition is invalid
         * @return this builder (fluid interface)
         */
        public NamedTypeRefBuilder<B> withComposite(String refName,
                String localTypeName) throws InvalidModelException {
            return withComposite(refName, namespace, version,
                    localTypeName);
        }

        /**
         * Defines a ValueType (same or external domain) as a composite of the
         * event.
         *
         * @param refName name for this composite type
         * @param compositeNamespace namespace of the composite type
         * @param compositeVersion verison of the composite type
         * @param compositeName type name of the composite type
         * @return this builder (fluid interface)
         * @throws InvalidModelException if this definition is invalid
         */
        public NamedTypeRefBuilder<B> withComposite(String refName,
                String compositeNamespace, String compositeVersion,
                String compositeName) throws InvalidModelException {

            NamedTypeRefBuilder<B> ref = new NamedTypeRefBuilder(this, refName,
                    compositeNamespace, compositeVersion, compositeName);

            if (!composites.add(ref)) {
                throw new DuplicateNamedTypeException(refName);
            }
            return ref;
        }

        protected Collection<NamedTypeRef<ValueType>> getCompositeRefs(
                TypeResolver resolver) throws InvalidModelException {
            return this.composites.toList((c) -> {
                NamedTypeRef ref = c.createModel(resolver);
                if (!ValueType.class.isAssignableFrom(
                        ref.getReferencedType().getClass())) {
                    throw new InvalidModelException(
                            String.format("Composites can only be a domain "
                                    + "ValueType, refererence '%s' but was "
                                    + "found to be of type '%s'",
                                    ref.toString(),
                                    ref.getReferencedType().getClass().getName()));
                }
                return ref;
            });
//            Collection<NamedTypeRef<ValueType>> compositeRefs
//                    = new ArrayList<>();
//            for (NamedTypeRefBuilder refBuilder : this.composites) {
//                NamedTypeRef ref = refBuilder.createModel(resolver);
//                if (!ValueType.class.isAssignableFrom(
//                        ref.getReferencedType().getClass())) {
//                    throw new InvalidModelException(
//                            String.format("Composites can only be a domain "
//                                    + "ValueType, refererence '%s' but was "
//                                    + "found to be of type '%s'",
//                                    ref.toString(),
//                                    ref.getReferencedType().getClass().getName()));
//                }
//                compositeRefs.add(ref);
//            }
//            return compositeRefs;
        }
    }

    public class NamedTypeRefBuilder<D> extends ModelBuilder {

        private final D declaringBuilder;
        private final String namespace;
        private final String version;
        private final String typeName;
        private final String refName;
        private String description;

        public NamedTypeRefBuilder(D declaringBuilder, String refName,
                String namespace, String version, String typeName) {
            this.declaringBuilder = declaringBuilder;
            this.namespace = namespace;
            this.version = version;
            this.typeName = typeName;
            this.refName = refName;
        }

        public NamedTypeRefBuilder withDescription(String desc) {
            this.description = desc;
            return this;
        }

        String getRefName() {
            return refName;
        }

        public D build() {
            return declaringBuilder;
        }

        @Override
        protected NamedTypeRef createModel(TypeResolver resolver)
                throws InvalidModelException {
            DomainType type = resolver.findType(namespace, version, typeName)
                    .orElseThrow(() -> new UnknownTypeException(
                            namespace, version, typeName)
                    );
            return new NamedTypeRef(refName, description, type);
        }
    }

    /**
     * Ensures that each element is named uniquely within the set.
     *
     * @param <V>
     */
    private class NamedSet<V> implements Set<V> {

        private final Map<String, V> items = new HashMap<>();
        private final Function<V, String> namer;

        public NamedSet(Function<V, String> namer) {
            this.namer = namer;
        }

        @Override
        public int size() {
            return items.size();
        }

        @Override
        public boolean isEmpty() {
            return items.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return items.values().contains(o);
        }

        @Override
        public Iterator<V> iterator() {
            return items.values().iterator();
        }

        @Override
        public Object[] toArray() {
            return items.values().toArray();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return items.values().toArray(a);
        }

        /**
         * Maps each item in the set to a new List.
         * <p>
         *
         * @param <T>
         * @param <X>
         * @param mapper
         * @return
         * @throws X
         */
        public <T, X extends Throwable> List<T> toList(
                ThrowingFunction<V, T, X> mapper) throws X {
            return toCollection(ArrayList::new, mapper);
        }

        public <T, C extends Collection<T>, X extends Throwable> C toCollection(
                Supplier<C> collectionSupplier,
                ThrowingFunction<V, T, X> mapper) throws X {
            C to = collectionSupplier.get();
            for (V v : this.items.values()) {
                to.add(mapper.apply(v));
            }
            return to;
        }

        public Optional<V> findByName(String name) {
            return Optional.ofNullable(items.get(name));
        }

        /**
         * Attempts to add the named ref to the set.
         *
         * @param item item
         * @return true if item was unique, otherwise false
         */
        @Override
        public boolean add(V item) {
            final String name = namer.apply(item);
            if (items.containsKey(name)) {
                return false;
            }
            items.put(name, item);
            return true;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return items.values().containsAll(c);
        }

        @Override
        public boolean addAll(Collection<? extends V> c) {
            for (V v : c) {
                boolean res = this.add(v);
                if (!res) {
                    return res;
                }
            }
            return true;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object o) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

    }
}
