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

import org.geoint.acetate.spi.model.TypeResolver;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
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
    private HierarchicalTypeResolver resolver;

    public DomainBuilder(String namespace, String version) {
        this(namespace, version, null);
    }

    public DomainBuilder(String namespace, String version,
            TypeResolver resolver) {
        this.namespace = namespace;
        this.version = version;

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
        this.resolver = HierarchicalTypeResolver.newHierarchy(new TypeBuilderResolver())
                .optionalChild(resolver)
                .addChild(new MemoryTypeResolver());
    }

    /**
     * Sets a domain model description.
     *
     * @param domainModelDescription domain model description
     * @return this builder
     * @throws IllegalStateException if the domain has already been built
     */
    public DomainBuilder withDescription(String domainModelDescription)
            throws IllegalStateException {
        verifyBuilderState();
        this.description = domainModelDescription;
        return this;
    }

    public ValueBuilder defineValue(String typeName)
            throws InvalidModelException, IllegalStateException {
        return defineType(ValueBuilder::new, typeName);
    }

    public ValueBuilder defineValue(String typeName,
            TypeCodec defaultCharCodec, TypeCodec defaultBinCodec)
            throws InvalidModelException, IllegalStateException {
        ValueBuilder b = defineType(ValueBuilder::new, typeName);
        return b.withDefaultCharCodec(defaultCharCodec.getClass())
                .withDefaultBinCodec(defaultBinCodec.getClass());
    }

    public ValueBuilder defineValue(String typeName, String desc)
            throws InvalidModelException {
        return defineType(ValueBuilder::new, typeName, desc);
    }

    public ValueBuilder defineValue(String typeName, String desc,
            TypeCodec defaultCharCodec, TypeCodec defaultBinCodec)
            throws InvalidModelException, IllegalStateException {
        ValueBuilder b = defineType(ValueBuilder::new, typeName, desc);
        return b.withDefaultBinCodec(defaultBinCodec.getClass())
                .withDefaultBinCodec(defaultBinCodec.getClass());
    }

    public EventBuilder defineEvent(String typeName)
            throws InvalidModelException {
        return defineType(EventBuilder::new, typeName);
    }

    public EventBuilder defineEvent(String typeName, String desc)
            throws InvalidModelException {
        return defineType(EventBuilder::new, typeName, desc);
    }

    public ResourceBuilder defineResource(String typeName)
            throws InvalidModelException {
        return defineType(ResourceBuilder::new, typeName);
    }

    public ResourceBuilder defineResource(String typeName, String desc)
            throws InvalidModelException {
        return defineType(ResourceBuilder::new, typeName, desc);
    }

    private <B extends TypeBuilder> B defineType(
            BiFunction<ThrowingFunction<B, DomainBuilder, InvalidModelException>, String, B> typeBuilderConstructor,
            String typeName) throws InvalidModelException {
        verifyBuilderState();
        if (typeBuilders.findByName(typeName).isPresent()) {
            throw new DuplicateNamedTypeException(typeName);
        }
        return typeBuilderConstructor.apply(this::registerType, typeName);
    }

    private <B extends TypeBuilder> B defineType(
            BiFunction<ThrowingFunction<B, DomainBuilder, InvalidModelException>, String, B> typeBuilderConstructor,
            String typeName, String desc) throws InvalidModelException {
        verifyBuilderState();
        if (typeBuilders.findByName(typeName).isPresent()) {
            throw new DuplicateNamedTypeException(typeName);
        }
        return (B) typeBuilderConstructor.apply(this::registerType, typeName)
                .withDescription(desc);
    }

    /**
     * Method reference {@link ThrowingFunction callback} that is called when a 
     * {@link TypeBuilder#build() } method is called, registering the type
     * builder with the domain builder and returning the domain builder (this
     * keeps the API quite fluid).
     *
     * @param type domain type builder
     * @return this builder
     * @throws InvalidModelException if the type
     */
    private DomainBuilder registerType(TypeBuilder type)
            throws InvalidModelException {
        verifyBuilderState();
        if (!typeBuilders.add(type)) {
            throw new DuplicateNamedTypeException(type.typeName);
        }
        return this;
    }

    public DomainModel build() throws InvalidModelException {

        if (model == null) {
            return DomainModel.newInstance(namespace, version, description,
                    typeBuilders.toList((t) -> (DomainType) t.createModel(resolver)));
        }
        return model;
    }

    private void verifyBuilderState() throws IllegalStateException {
        if (model != null) {
            throw new IllegalStateException("DomainBuilder has been built "
                    + "and can no longer be modified.");
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
            return new EventType(namespace, version, this.typeName,
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

            if (!links.add(ref)) {
                throw new DuplicateNamedTypeException(refName);
            }
            return ref;
        }

        @Override
        protected ResourceType createModel(TypeResolver resolver)
                throws InvalidModelException {

            return new ResourceType(namespace, version, typeName, description,
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
            return new ValueType(namespace, version, typeName, description,
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

        final NamedSet<NamedRefBuilder> composites
                = new NamedSet<>(NamedRefBuilder::getRefName);

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
         * Defines composite reference.
         *
         * @param refName name for this composite type
         * @throws InvalidModelException if this definition is invalid
         * @return this builder (fluid interface)
         */
        public NamedRefBuilder<B> withComposite(String refName)
                throws InvalidModelException {
            NamedRefBuilder<B> rb = new NamedRefBuilder(this, refName);
            addCompositeRef(rb);
            return rb;
        }

        /**
         * Defines a reference to a (or a collection of) DomainType(s) as a
         * composite.
         *
         * @param refName name for this composite type
         * @param compositeNamespace namespace of the composite type
         * @param compositeVersion verison of the composite type
         * @param compositeName type name of the composite type
         * @return this builder (fluid interface)
         * @throws InvalidModelException if this definition is invalid
         */
        public NamedTypeRefBuilder<B> withCompositeType(String refName,
                String compositeNamespace, String compositeVersion,
                String compositeName) throws InvalidModelException {

            NamedRefBuilder<B> ref = new NamedRefBuilder(this, refName);
            NamedTypeRefBuilder<B> typeRef
                    = ref.referencedType(compositeNamespace,
                            compositeVersion, compositeName);
            addCompositeRef(ref);
            return typeRef;
        }

        /**
         * Returns a NamedTypeRefBuilder to define the composite type.
         *
         * @param refName
         * @param localDomainTypeName
         * @return
         * @throws InvalidModelException
         */
        public NamedTypeRefBuilder<B> withCompositeType(String refName,
                String localDomainTypeName) throws InvalidModelException {
            NamedRefBuilder<B> ref = new NamedRefBuilder(this, refName);
            NamedTypeRefBuilder<B> typeRef = ref.referencedType(localDomainTypeName);
            addCompositeRef(ref);
            return typeRef;
        }

        /**
         * Returns a NamedMapRefBuilder to define the composite map.
         *
         * @param refName
         * @return
         * @throws InvalidModelException
         */
        public NamedMapRefBuilder<B> withCompositeMap(String refName)
                throws InvalidModelException {
            NamedRefBuilder<B> ref = new NamedRefBuilder(this, refName);
            NamedMapRefBuilder<B> typeRef = ref.referencedMap();
            addCompositeRef(ref);
            return typeRef;
        }

        protected void addCompositeRef(NamedRefBuilder ref)
                throws InvalidModelException {
            if (!composites.add(ref)) {
                throw new DuplicateNamedTypeException(ref.getRefName());
            }
        }

        protected Collection<NamedRef> getCompositeRefs(
                TypeResolver resolver) throws InvalidModelException {
            return this.composites.toList((c) -> {
                return c.createModel(resolver);
            });
        }
    }

    public abstract class RefModelBuilder<M extends NamedRef> extends ModelBuilder<M> {

        abstract String getRefName();
    }

    /**
     * Generic named ref API.
     *
     * @see NamedTypeRefBuilder
     * @see NamedMapRefBuilder
     * @param <B> declaring builder
     */
    public class NamedRefBuilder<B extends ModelBuilder>
            extends ModelBuilder {

        private final B declaringBuilder;
        private final String refName;
        private String description;
        private RefModelBuilder refModelBuilder; //aggregate

        public NamedRefBuilder(B declaringBuilder, String refName) {
            this.declaringBuilder = declaringBuilder;
            this.refName = refName;
        }

        String getRefName() {
            return refName;
        }

        public NamedRefBuilder<B> withDescription(String desc) {
            this.description = desc;
            return this;
        }

        public NamedTypeRefBuilder<B> referencedType(String typeName) {
            refModelBuilder = new NamedTypeRefBuilder(this, typeName);
            return (NamedTypeRefBuilder<B>) refModelBuilder;
        }

        public NamedTypeRefBuilder<B> referencedType(String typeNamespace,
                String typeVersion, String typeName) {
            refModelBuilder = new NamedTypeRefBuilder(this, typeNamespace,
                    typeVersion, typeName);
            return (NamedTypeRefBuilder<B>) refModelBuilder;
        }

        public NamedMapRefBuilder<B> referencedMap() {
            refModelBuilder = new NamedMapRefBuilder(this);
            return (NamedMapRefBuilder<B>) refModelBuilder;
        }

        @Override
        protected NamedRef createModel(TypeResolver resolver)
                throws InvalidModelException {
            return (NamedRef) refModelBuilder.createModel(resolver);
        }

        public B build() {
            return declaringBuilder;
        }
    }

    public class NamedTypeRefBuilder<B extends ModelBuilder> extends RefModelBuilder<NamedTypeRef> {

        private final NamedRefBuilder<B> refBuilder; //aggregation vs inheritence because generics resulted in a too-complex API
        private final String refTypeNamespace;
        private final String refTypeVersion;
        private final String refTypeName;
        private boolean collection;

        public NamedTypeRefBuilder(NamedRefBuilder<B> refBuilder,
                String refNamespace, String refVersion, String refType) {
            this.refBuilder = refBuilder;
            this.refTypeNamespace = refNamespace;
            this.refTypeVersion = refVersion;
            this.refTypeName = refType;
        }

        public NamedTypeRefBuilder(NamedRefBuilder<B> refBuilder,
                String refType) {
            this(refBuilder, namespace, version, refType);
        }

        public NamedTypeRefBuilder(B builder, String refName,
                String refNamespace, String refVersion, String refType) {
            this(new NamedRefBuilder(builder, refName), refNamespace,
                    refVersion, refType);
        }

        public NamedTypeRefBuilder<B> withDescription(String desc) {
            this.refBuilder.description = desc;
            return this;
        }

        public NamedTypeRefBuilder<B> isCollection(boolean collection) {
            this.collection = collection;
            return this;
        }

        @Override
        String getRefName() {
            return refBuilder.getRefName();
        }

        public B build() {
            return refBuilder.build();
        }

        @Override
        protected NamedTypeRef createModel(TypeResolver resolver)
                throws InvalidModelException {
            DomainType type = resolver.resolve(refTypeNamespace, refTypeVersion, refTypeName)
                    .orElseThrow(() -> new UnknownTypeException(
                            refTypeNamespace, refTypeVersion, refTypeName)
                    );
            return new NamedTypeRef(type,
                    refBuilder.refName,
                    refBuilder.description,
                    collection);
        }

    }

    public class NamedMapRefBuilder<B extends ModelBuilder>
            extends RefModelBuilder<NamedMapRef> {

        private final NamedRefBuilder<B> refBuilder;
        private NamedTypeRefBuilder<NamedMapRefBuilder<B>> keyRefBuilder;
        private NamedRefBuilder<NamedMapRefBuilder<B>> valueRefBuilder;

        public NamedMapRefBuilder(NamedRefBuilder<B> refBuilder) {
            this.refBuilder = refBuilder;
        }

        public NamedMapRefBuilder<B> withDescription(String desc) {
            this.refBuilder.description = desc;
            return this;
        }

        /**
         * Specify the key type as a domain type of the domain being built.
         *
         * @param typeName domain type name
         * @return this builder
         */
        public NamedTypeRefBuilder<NamedMapRefBuilder<B>> keyType(String typeName) {
            this.keyRefBuilder = new NamedTypeRefBuilder(new NamedRefBuilder(this, typeName), typeName);
            return this.keyRefBuilder;
        }

        public NamedTypeRefBuilder<NamedMapRefBuilder<B>> keyType(String refName,
                String refTypeName) {
            this.keyRefBuilder = new NamedTypeRefBuilder(
                    new NamedRefBuilder(this, refName), refTypeName);
            return this.keyRefBuilder;
        }

        public NamedTypeRefBuilder<NamedMapRefBuilder<B>> keyType(String refName,
                String refNamespace, String refVersion, String refType) {
            this.keyRefBuilder = new NamedTypeRefBuilder(
                    new NamedRefBuilder(this, refName), refNamespace, refVersion, refType);
            return this.keyRefBuilder;
        }

        public NamedRefBuilder<NamedMapRefBuilder<B>> valueRef(String refName) {
            this.valueRefBuilder = new NamedRefBuilder(this, refName);
            return this.valueRefBuilder;
        }

        @Override
        protected NamedMapRef createModel(TypeResolver resolver)
                throws InvalidModelException {
            NamedTypeRef keyRef = keyRefBuilder.createModel(resolver);
            NamedRef valueRef = valueRefBuilder.createModel(resolver);
            return new NamedMapRef(this.refBuilder.refName,
                    this.refBuilder.description, keyRef, valueRef);
        }

        @Override
        String getRefName() {
            return refBuilder.getRefName();
        }

        public B build() {
            return refBuilder.build();
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

    /**
     * Attempts to resolver a DomainType the collection of TypeBuilders
     */
    private class TypeBuilderResolver implements TypeResolver {

        @Override
        public Optional<DomainType> resolve(String ns,
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

}
