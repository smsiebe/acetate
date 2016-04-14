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

import org.geoint.acetate.model.resolve.MapTypeResolver;
import org.geoint.acetate.model.resolve.HierarchicalTypeResolver;
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
import org.geoint.acetate.EventInstance;
import org.geoint.acetate.InstanceRef;
import org.geoint.acetate.ResourceInstance;
import org.geoint.acetate.functional.ThrowingBiFunction;
import org.geoint.acetate.functional.ThrowingConsumer;
import org.geoint.acetate.functional.ThrowingFunction;
import org.geoint.acetate.model.resolve.DomainTypeResolver;

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
    private HierarchicalTypeResolver<TypeDescriptor> resolver;

    public DomainBuilder(String namespace, String version) {
        this(namespace, version, null);
    }

    public DomainBuilder(String namespace, String version,
            DomainTypeResolver<TypeDescriptor> resolver) {
        this.namespace = namespace;
        this.version = version;

        /*
             * types are resolved in the following sequence, returning the first
             * resolution found:
             *  1) if the type is for this domain, check the collection of 
             *     types that have already been created during this build process
             *  2) if a DomainTypeResolver was provided (constructor), attempt to 
             *     resolveType from it
             *  3) if the type is for this domain, attempt to resolveType the type 
             *     from any remaining domain type definitions 
             *  4) throw UnknownTypeException
         */
        this.resolver = HierarchicalTypeResolver.newHierarchy(new TypeBuilderResolver())
                .optionalChild(resolver)
                .addChild(new MapTypeResolver<>());
    }

    public String getNamespace() {
        return namespace;
    }

    public String getVersion() {
        return version;
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

    public ValueBuilder defineValue(String typeName, String desc)
            throws InvalidModelException {
        return defineType(ValueBuilder::new, typeName, desc);
    }

    public void defineValueIfAbsent(String typeName,
            ThrowingConsumer<ValueBuilder, InvalidModelException> callback)
            throws InvalidModelException {
        if (!typeBuilders.findByName(typeName).isPresent()) {
            ValueBuilder vb = new ValueBuilder(this::registerType, typeName);
            callback.consume(vb);
        }
    }

    public EventBuilder defineEvent(String typeName)
            throws InvalidModelException {
        return defineType(EventBuilder::new, typeName);
    }

    public EventBuilder defineEvent(String typeName, String desc)
            throws InvalidModelException {
        return defineType(EventBuilder::new, typeName, desc);
    }

    public void defineEventIfAbsent(String typeName,
            ThrowingConsumer<EventBuilder, InvalidModelException> callback)
            throws InvalidModelException {
        if (!typeBuilders.findByName(typeName).isPresent()) {
            EventBuilder eb = new EventBuilder(this::registerType, typeName);
            callback.consume(eb);
        }
    }

    public ResourceBuilder defineResource(String typeName)
            throws InvalidModelException {
        return defineType(ResourceBuilder::new, typeName);
    }

    public ResourceBuilder defineResource(String typeName, String desc)
            throws InvalidModelException {
        return defineType(ResourceBuilder::new, typeName, desc);
    }

    public void defineResourceIfAbsent(String typeName,
            ThrowingConsumer<ResourceBuilder, InvalidModelException> callback)
            throws InvalidModelException {
        if (!typeBuilders.findByName(typeName).isPresent()) {
            ResourceBuilder rb = new ResourceBuilder(this::registerType, typeName);
            callback.consume(rb);
        }
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
            return DomainModel.newModel(namespace, version, description,
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

        public abstract String getName();

        /**
         * Creates the domain model component from this builder.
         *
         * @param resolver domain type resolver that may be used to resolveType
         * references
         * @return domain model
         * @throws InvalidModelException if the type definition is not valid
         */
        protected abstract M createModel(DomainTypeResolver<TypeDescriptor> resolver)
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

        @Override
        public String getName() {
            return typeName;
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
        protected EventType createModel(DomainTypeResolver<TypeDescriptor> resolver)
                throws InvalidModelException {
            return new EventType(namespace, version, this.typeName,
                    this.description, getCompositeRefs(resolver));
        }

    }

    public class BuiltOperationBuilder extends ModelBuilder<ResourceOperation> {

        private final ResourceOperation op;

        public BuiltOperationBuilder(ResourceOperation op) {
            this.op = op;
        }

        @Override
        public String getName() {
            return op.getName();
        }

        @Override
        protected ResourceOperation createModel(DomainTypeResolver<TypeDescriptor> resolver) throws InvalidModelException {
            return op;
        }

    }

    public class OperationBuilder extends ModelBuilder<ResourceOperation> {

        private final ResourceBuilder resource;
        private final String operationName;
        private String description;
        private boolean idempotent = false;
        private boolean safe = false;
        private ThrowingBiFunction<ResourceInstance, InstanceRef[], EventInstance, ?> function;
        private NamedSet<NamedTypeRefBuilder> parameters;
        private NamedTypeRefBuilder returnEvent;

        public OperationBuilder(ResourceBuilder rb, String operationName) {
            this.resource = rb;
            this.operationName = operationName;
        }

        public String getName() {
            return operationName;
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

        public OperationBuilder withFunction(
                ThrowingBiFunction<ResourceInstance, InstanceRef[], EventInstance, ?> function)
                throws InvalidModelException {
            this.function = function;
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
        protected ResourceOperation createModel(DomainTypeResolver<TypeDescriptor> resolver)
                throws InvalidModelException {
            return new ResourceOperation(namespace, version,
                    this.resource.typeName, operationName, description,
                    idempotent, safe, function,
                    parameters.toList((r) -> r.createModel(resolver)),
                    returnEvent.createModel(resolver));
        }

    }

    public class ResourceBuilder extends ComposedTypeBuilder<ResourceType, ResourceBuilder> {

        final NamedSet<ModelBuilder<ResourceOperation>> operations
                = new NamedSet<>(ModelBuilder::getName);

        public ResourceBuilder(ThrowingFunction<ResourceBuilder, DomainBuilder, InvalidModelException> onBuild,
                String typeName) {
            super(onBuild, typeName);
        }

        public ResourceBuilder(ThrowingFunction<ResourceBuilder, DomainBuilder, InvalidModelException> onBuild,
                String typeName, String description) {
            super(onBuild, typeName, description);
        }

        public OperationBuilder withOperation(String opName)
                throws InvalidModelException {
            OperationBuilder op = new OperationBuilder(this, opName);
            addOperation(op);
            return op;
        }

        public ResourceBuilder withOperation(ResourceOperation operation)
                throws InvalidModelException {
            addOperation(new BuiltOperationBuilder(operation));
            return this;
        }

        protected void addOperation(ModelBuilder<ResourceOperation> opBuilder)
                throws InvalidModelException {
            if (!operations.add(opBuilder)) {
                throw new DuplicateNamedTypeException(opBuilder.getName());
            }
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
            addCompositeRef(ref);
            return ref;
        }

        /**
         * Defines a link with previously resolved reference model.
         *
         * @param ref reference model
         * @return this builder
         * @throws InvalidModelException if invalid definition
         */
        public ResourceBuilder withLink(NamedTypeRef ref)
                throws InvalidModelException {
            NamedTypeRefBuilder<ResourceBuilder> refBuilder
                    = new NamedTypeRefBuilder(this, ref);
            addCompositeRef(refBuilder);
            return this;
        }

        @Override
        protected ResourceType createModel(DomainTypeResolver<TypeDescriptor> resolver)
                throws InvalidModelException {

            return new ResourceType(namespace, version, typeName, description,
                    getCompositeRefs(resolver),
                    operations.toList((o) -> o.createModel(resolver)));
        }
    }

    public class ValueBuilder extends TypeBuilder<ValueType, ValueBuilder> {

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

        @Override
        protected ValueType createModel(DomainTypeResolver<TypeDescriptor> resolver)
                throws InvalidModelException {
            return new ValueType(namespace, version, typeName, description);
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

            NamedTypeRefBuilder<B> ref = new NamedTypeRefBuilder(this, refName,
                    compositeNamespace, compositeVersion, compositeName);
            addCompositeRef(ref);
            return ref;
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
            NamedTypeRefBuilder<B> ref = new NamedTypeRefBuilder(this, refName, localDomainTypeName);
            addCompositeRef(ref);
            return ref;
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
            NamedMapRefBuilder<B> ref = new NamedMapRefBuilder(this, refName);
            addCompositeRef(ref);
            return ref;
        }

        public ComposedTypeBuilder<T, B> withComposite(NamedRef compositeRef)
                throws InvalidModelException {
            BuiltNamedRef ref = new BuiltNamedRef(this, compositeRef);
            addCompositeRef(ref);
            return this;
//            if (compositeRef instanceof NamedTypeRef) {
//                return withCompositeType((NamedTypeRef) compositeRef);
//            } else if (compositeRef instanceof NamedMapRef) {
//                return withCompositeMap((NamedMapRef) compositeRef);
//            } else {
//                throw new InvalidModelException(String.format("Unknown "
//                        + "reference type '%s'.",
//                        compositeRef.getClass().getName()));
//            }

        }

        protected void addCompositeRef(NamedRefBuilder ref)
                throws InvalidModelException {
            if (!composites.add(ref)) {
                throw new DuplicateNamedTypeException(ref.getRefName());
            }
        }

        protected Collection<NamedRef> getCompositeRefs(
                DomainTypeResolver<TypeDescriptor> resolver) throws InvalidModelException {
            Collection<NamedRef> refs = new ArrayList<>();
            for (NamedRefBuilder b : this.composites) {
                refs.add(b.createModel(resolver));
            }
            return refs;
        }
    }

    /**
     * Generic named ref API.
     *
     * @see NamedTypeRefBuilder
     * @see NamedMapRefBuilder
     * @param <B> declaring builder
     * @param <T> type of model ref
     */
    public abstract class NamedRefBuilder<B extends ModelBuilder, T extends NamedRef>
            extends ModelBuilder<T> {

        protected final B declaringBuilder;
        protected final String refName;
        protected String description;

        public NamedRefBuilder(B declaringBuilder, String refName) {
            this.declaringBuilder = declaringBuilder;
            this.refName = refName;
        }

        @Override
        public String getName() {
            return getRefName();
        }

        String getRefName() {
            return refName;
        }

        public B build() {
            return declaringBuilder;
        }

        @Override
        protected abstract T createModel(DomainTypeResolver<TypeDescriptor> resolver)
                throws InvalidModelException;

    }

    /**
     * NamedRef builder used when the reference has already been built.
     *
     * @param <T>
     * @param <B>
     */
    public class BuiltNamedRef<T extends NamedRef, B extends ModelBuilder>
            extends NamedRefBuilder<B, T> {

        private T ref;

        public BuiltNamedRef(B declaringBuilder, T ref) {
            super(declaringBuilder, ref.getName());
            this.ref = ref;
        }

        @Override
        protected T createModel(DomainTypeResolver<TypeDescriptor> resolver)
                throws InvalidModelException {
            return ref;
        }
    }

    public class NamedTypeRefBuilder<B extends ModelBuilder>
            extends NamedRefBuilder<B, NamedTypeRef> {

        private final TypeDescriptor refTypeDescriptor;
        private boolean collection;

        public NamedTypeRefBuilder(B declaringBuilder, String refName,
                String refNamespace, String refVersion, String refType) {
            super(declaringBuilder, refName);
            this.refTypeDescriptor = new TypeDescriptor(refNamespace, refVersion, refType);
        }

        public NamedTypeRefBuilder(B declaringBuilder,
                String refName, String refType) {
            this(declaringBuilder, refName, namespace, version, refType);
        }

        public NamedTypeRefBuilder(B builder, NamedTypeRef ref) {
            super(builder, ref.getName());
            this.refTypeDescriptor
                    = new TypeDescriptor(ref.getReferencedType().getNamespace(),
                            ref.getReferencedType().getVersion(),
                            ref.getReferencedType().getName());
            this.description = ref.getDescription().orElse(null);
            this.collection = ref.isCollection();
        }

        public NamedTypeRefBuilder<B> withDescription(String desc) {
            this.description = desc;
            return this;
        }

        public NamedTypeRefBuilder<B> isCollection(boolean collection) {
            this.collection = collection;
            return this;
        }

        @Override
        protected NamedTypeRef createModel(DomainTypeResolver<TypeDescriptor> resolver)
                throws InvalidModelException {
            Optional<DomainType> type = resolver.resolveType(refTypeDescriptor);
            if (!type.isPresent()) {
                throw new UnknownTypeException(refTypeDescriptor);
            }
            return new NamedTypeRef(type.get(),
                    refName,
                    description,
                    collection);
        }

    }

    public class NamedMapRefBuilder<B extends ModelBuilder>
            extends NamedRefBuilder<B, NamedMapRef> {

        private NamedTypeRefBuilder<NamedMapRefBuilder<B>> keyRefBuilder;
        private NamedRefBuilder<NamedMapRefBuilder<B>, ?> valueRefBuilder;

        public NamedMapRefBuilder(B declaringBuilder, String refName) {
            super(declaringBuilder, refName);
        }

        public NamedMapRefBuilder(B declaringBuilder, String refName,
                NamedTypeRefBuilder<NamedMapRefBuilder<B>> keyRefBuilder,
                NamedRefBuilder<NamedMapRefBuilder<B>, ?> valueRefBuilder) {
            super(declaringBuilder, refName);
            this.keyRefBuilder = keyRefBuilder;
            this.valueRefBuilder = valueRefBuilder;
        }

        public NamedMapRefBuilder(B builder, NamedMapRef ref)
                throws InvalidModelException {
            super(builder, ref.getName());
            this.keyRefBuilder = new NamedTypeRefBuilder(builder, ref.getKeyRef());
            if (ref.getValueRef() instanceof NamedTypeRef) {
                this.valueRefBuilder = new NamedTypeRefBuilder(builder, (NamedTypeRef) ref.getValueRef());
            } else if (ref.getValueRef() instanceof NamedMapRef) {
                this.valueRefBuilder = new NamedMapRefBuilder(builder, (NamedMapRef) ref.getValueRef());
            } else {
                throw new InvalidModelException(String.format("Unknown map "
                        + "value model reference type '%s'",
                        ref.getValueRef().getClass().getName()));
            }
        }

        public NamedMapRefBuilder<B> withDescription(String desc) {
            this.description = desc;
            return this;
        }

        /**
         * Specify the key type as a domain type of the domain being built.
         *
         * @param typeName domain type name
         * @return this builder
         */
        public NamedTypeRefBuilder<NamedMapRefBuilder<B>> keyType(String typeName) {
            this.keyRefBuilder = new NamedTypeRefBuilder(this, typeName, typeName);
            return this.keyRefBuilder;
        }

        public NamedTypeRefBuilder<NamedMapRefBuilder<B>> keyType(String refName,
                String typeName) {
            this.keyRefBuilder = new NamedTypeRefBuilder(this, refName, typeName);
            return this.keyRefBuilder;
        }

        public NamedTypeRefBuilder<NamedMapRefBuilder<B>> keyType(String refName,
                String refNamespace, String refVersion, String refType) {
            this.keyRefBuilder = new NamedTypeRefBuilder(this, refName, refNamespace, refVersion, refType);
            return this.keyRefBuilder;
        }

        public NamedTypeRefBuilder<NamedMapRefBuilder<B>> valueType(String typeName) {
            this.valueRefBuilder = new NamedTypeRefBuilder(this, typeName, typeName);
            return (NamedTypeRefBuilder<NamedMapRefBuilder<B>>) this.valueRefBuilder;
        }

        public NamedTypeRefBuilder<NamedMapRefBuilder<B>> valueType(String refName,
                String typeName) {
            this.valueRefBuilder = new NamedTypeRefBuilder(this, refName, typeName);
            return (NamedTypeRefBuilder<NamedMapRefBuilder<B>>) this.valueRefBuilder;
        }

        public NamedTypeRefBuilder<NamedMapRefBuilder<B>> valueType(String refName,
                String refNamespace, String refVersion, String refType) {
            this.valueRefBuilder = new NamedTypeRefBuilder(this, refName,
                    refNamespace, refVersion, refType);
            return (NamedTypeRefBuilder<NamedMapRefBuilder<B>>) this.valueRefBuilder;
        }

        public NamedMapRefBuilder<NamedMapRefBuilder<B>> valueMap(String refName) {
            this.valueRefBuilder = new NamedMapRefBuilder(this, refName);
            return (NamedMapRefBuilder<NamedMapRefBuilder<B>>) this.valueRefBuilder;
        }

        @Override
        protected NamedMapRef createModel(DomainTypeResolver<TypeDescriptor> resolver)
                throws InvalidModelException {
            NamedTypeRef keyRef = keyRefBuilder.createModel(resolver);
            NamedRef valueRef = valueRefBuilder.createModel(resolver);
            return new NamedMapRef(this.refName,
                    this.description, keyRef, valueRef);
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
    private class TypeBuilderResolver implements DomainTypeResolver<TypeDescriptor> {

        @Override
        public Optional<DomainType> resolveType(TypeDescriptor td) {
            Optional<TypeBuilder> typeBuilder = typeBuilders.stream()
                    .filter((b) -> namespace.contentEquals(td.getNamespace()))
                    .filter((b) -> version.contentEquals(td.getVersion()))
                    .filter((b) -> b.typeName.contentEquals(td.getType()))
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
