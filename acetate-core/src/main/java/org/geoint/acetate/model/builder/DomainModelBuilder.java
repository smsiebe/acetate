package org.geoint.acetate.model.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.geoint.acetate.data.transform.ComplexObjectCodec;
import org.geoint.acetate.impl.model.DomainModelImpl;
import org.geoint.acetate.impl.model.DomainUtil;
import org.geoint.acetate.impl.model.ImmutableContextPath;
import org.geoint.acetate.impl.transform.DefaultBooleanCodec;
import org.geoint.acetate.impl.transform.DefaultIntegerCodec;
import org.geoint.acetate.impl.transform.DefaultStringCodec;
import org.geoint.acetate.impl.model.ImmutableContextPath.ImmutableCollectionPath;
import org.geoint.acetate.impl.model.ImmutableContextPath.ImmutableMapPath;
import org.geoint.acetate.impl.model.ImmutableInheritedObjectModel;
import org.geoint.acetate.impl.model.ImmutableObjectModel;
import org.geoint.acetate.model.ModelContextPath;
import org.geoint.acetate.model.ContextualComponent;
import org.geoint.acetate.model.ContextualObjectModel;
import org.geoint.acetate.model.constraint.ComponentConstraint;
import org.geoint.acetate.model.DomainObject;
import org.geoint.acetate.model.DomainObjectOperation;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.InheritedObjectModel;
import org.geoint.acetate.model.ModelException;
import org.geoint.acetate.model.ContextualCollectionModel;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.ObjectRegistry;

/**
 * API to programmatically build a DomainModel.
 *
 */
public class DomainModelBuilder {

    private final String name;
    private final long version;
    private String description;
    private Map<String, ObjectModelBuilderImpl<?>> components;
    private static DomainModel DEFAULT_MODEL;

    private final static Logger logger
            = Logger.getLogger(DomainModelBuilder.class.getName());

    private DomainModelBuilder(String modelName, long modelVersion) {
        this.name = modelName;
        this.version = modelVersion;
    }

    /**
     * Create a new domain model, from scratch, without any model defaults (ie
     * default components, codecs, etc).
     *
     * @param modelName new domain model name
     * @param modelVersion new domain model version
     * @return empty domain model
     */
    public static DomainModelBuilder newModel(String modelName, long modelVersion) {
        return new DomainModelBuilder(modelName, modelVersion);
    }

    /**
     * Creates a new domain model by extending from an existing model.
     *
     * @param modelName new domain model name
     * @param modelVersion new domain model version
     * @param model model to copy from
     * @return domain model seeded from provided model
     */
    public static DomainModelBuilder extend(String modelName, long modelVersion,
            DomainModel model) {
        DomainModelBuilder b = new DomainModelBuilder(modelName, modelVersion);
        b.copyFrom(model);
        return b;
    }

    /**
     * Creates a new domain model seeding common model artifacts from the
     * default domain model.
     *
     * @param modelName new domain model name
     * @param modelVersion new domain model version
     * @return domain model builder seeded from default domain model
     * @throws ModelException if there were problems creating the default model
     */
    public static DomainModelBuilder extendDefault(String modelName,
            long modelVersion) throws ModelException {
        return extend(modelName, modelVersion, defaultModel());
    }

    public DomainModelBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Add a component to the domain model.
     *
     * @param componentName domain-unique component name
     * @return builder for this component model
     */
    public ObjectModelBuilder<?> component(String componentName) {
        if (components.containsKey(componentName)) {
            components.put(componentName,
                    new ObjectModelBuilderImpl(componentName));
        }
        return components.get(componentName);
    }

    /**
     * Add a component to the domain model.
     *
     * @param <T> data type
     * @param componentName domain-unique component name
     * @param codec object codec
     * @return builder for this component model
     */
    public <T> ObjectModelBuilder<T> component(String componentName,
            ComplexObjectCodec<T> codec) {
        if (components.containsKey(componentName)) {
            components.put(componentName,
                    new ObjectModelBuilderImpl(componentName));
        }
        return (ObjectModelBuilder<T>) components.get(componentName);
    }

    /**
     * Create the domain model.
     *
     * @return domain model
     * @throws IncompleteModelException thrown if the domain model requires
     * additional artifacts to be a valid domain model
     * @throws ComponentCollisionException thrown if there was a component
     * naming collision within the domain model
     */
    public DomainModel build()
            throws IncompleteModelException, ComponentCollisionException {

        final InternalObjectRegistry objectRegistry
                = new InternalObjectRegistry(this.name, this.version);

        //build each "base" component model respecting inheritence first
        for (Entry<String, ObjectModelBuilderImpl<?>> e
                : components.entrySet()) {
            //for each base object model
            objectRegistry.register(
                    buildObjectModel(e.getValue(), objectRegistry)
            );
        }

        //build the composite model definitions as needed
        return new DomainModelImpl(objectRegistry,
                DomainUtil.uniqueDomainId(name, version),
                this.name, this.version, this.name, this.description);
    }

    private DomainObject<?> buildObjectModel(
            final ObjectModelBuilderImpl cb,
            final ObjectRegistry registry)
            throws IncompleteModelException, ComponentCollisionException {

        final String componentName = cb.componentName;
        final ImmutableContextPath objectPath
                = ImmutableContextPath.basePath(name, version, componentName);

        if (registry.findByName(componentName).isPresent()) {
            //registry already has a component by this name, don't even 
            //bother building
            throw new ComponentCollisionException(objectPath);
        }

        if (cb.inheritedComponents.isEmpty()) {
            //non-inherited object model
            return ImmutableObjectModel.base(
                    objectPath,
                    componentName,
                    cb.description,
                    cb.codec,
                    cb.operations.values(),
                    cb.compositeComponents,
                    cb.constraints,
                    cb.attributes);
        } else {
            //inherited object model
            return ImmutableInheritedObjectModel.base(
                    objectPath,
                    componentName,
                    cb.description,
                    cb.codec,
                    cb.operations.values(),
                    cb.compositeComponents,
                    cb.constraints,
                    cb.attributes);
        }
    }

    private <C extends ContextualComponent> deferContextual(
            final ObjectRegistry registry,
            ImmutableContextPath baseComponent,
            Map<String, ContextualBuilder<C>> contextual) {

    }
    /*
     * Converts a map of composite objects to composite object instances.
     */

    private Collection<ContextualComponent> buildContextual(
            final ObjectRegistry registry,
            ImmutableContextPath baseComponent,
            Map<String, ContextualBuilder> compositeBuilders) {
        Collection<ContextualComponent> composites = new ArrayList<>();
        for ( ) {
            return composites.entrySet().stream()
                    .map((e) -> buildComposite(
                                    registry,
                                    baseComponent.compositePath(e.getKey()),
                                    e.getValue())
                    )
                    .collect(Collectors.toList());
        }
    }

    private ContextualObjectModel buildComposite(ObjectRegistry registry,
            ImmutableContextPath compositePath,
            ValueContextBuilderImpl cb) throws IncompleteModelException {

//        for (String compositeComponentName
//                : compositeContext.getComponentDependencies()) {
//            if (!components.containsKey(compositeComponentName)) {
//                // ensure that all component dependencies have been 
//                //registered with the domain model (even it it hasn't been 
//                //built yet)
//                throw new IncompleteModelException(this.name,
//                        this.version,
//                        "Composite component '" + componentName
//                        + "' depends on a component named '"
//                        + compositeComponentName + "' (locally named '"
//                        + compositeLocalName
//                        + "') which could not be found "
//                        + "in the domain model.");
//            }
//        }
    }

    private ContextualCollectionModel buildCompositeCollection(ObjectRegistry registry,
            ImmutableContextPath compositePath,
            CollectionContextBuilderImpl cb) throws IncompleteModelException {

    }

    private ObjectMapModel buildCompositeMap(ObjectRegistry registry,
            ImmutableContextPath compositePath,
            MapContextBuilderImpl cb) throws IncompleteModelException {

    }

    private static DomainModel defaultModel()
            throws ComponentCollisionException {
        if (DEFAULT_MODEL == null) {
            DomainModelBuilder db = new DomainModelBuilder("DEFAULT", 1);
            db.component("java.util.String", new DefaultStringCodec());
            db.component("java.lang.Integer", new DefaultIntegerCodec());
            db.component("java.lang.Boolean", new DefaultBooleanCodec());

            try {
                //TODO add the rest
                DEFAULT_MODEL = db.build();
            } catch (IncompleteModelException ex) {
                //we should never get here
                assert false : "default domain model was incomplete";
                logger.log(Level.SEVERE, "Unable to create default domain "
                        + "model.", ex);
            }
        }
        return DEFAULT_MODEL;
    }

    /**
     * Copies the state of the provided domain model to this model.
     *
     * @param model
     */
    private void copyFrom(DomainModel model) {

    }

    private class ObjectModelBuilderImpl<T>
            implements ObjectModelBuilder<T> {

        private final String componentName;
        private String description;
        private ComplexObjectCodec<T> codec;
        private final Set<ComponentConstraint> constraints = new HashSet<>();
        private final Set<ComponentAttribute> attributes = new HashSet<>();
        //key = local name, value = composite component name
        private final Map<String, ContextualBuilder> compositeComponents
                = new TreeMap<>();
        private final Set<String> inheritedComponents = new HashSet<>();
        private final Map<String, DomainObjectOperation> operations
                = new HashMap<>();

        public ObjectModelBuilderImpl(String componentName) {
            this.componentName = componentName;
        }

        public ObjectModelBuilderImpl(String componentName,
                ComplexObjectCodec<T> codec) {
            this.componentName = componentName;
            this.codec = codec;
        }

        @Override
        public ObjectModelBuilder<T> description(String description) {
            this.description = description;
            return this;
        }

        @Override
        public ObjectModelBuilder<T> constraint(
                ComponentConstraint constraint) {
            constraints.add(constraint);
            return this;
        }

        @Override
        public ObjectModelBuilder<T> attribute(ComponentAttribute attribute) {
            this.attributes.add(attribute);
            return this;
        }

        @Override
        public ObjectModelBuilder<T> codec(ComplexObjectCodec<T> codec) {
            this.codec = codec;
            return this;
        }

        @Override
        public ContextualObjectModelBuilder<?> composite(String localName,
                String componentName) {
            return composite(localName,
                    new ContextualObjectModelBuilderImpl(componentName));
        }

        @Override
        public ContextualObjectModelBuilder<?> compositeCollection(String localName,
                String componentName) {
            return composite(localName,
                    new ContextualObjectCollectionBuilderImpl(componentName));
        }

        @Override
        public ContextualObjectMapBuilder<?, ?> compositeMap(String localName,
                String keyComponentName, String valueComponentName) {
            return composite(localName,
                    new ContextualObjectMapBuilderImpl(keyComponentName,
                            valueComponentName));
        }

        private <B extends ContextualBuilder> B composite(String localName,
                B ref) {
            compositeComponents.put(localName, ref);
            return ref;
        }

        @Override
        public ObjectModelBuilder<T> specializes(String parentComponentName) {
            inheritedComponents.add(parentComponentName);
            return this;
        }

        @Override
        public ObjectModelBuilder<T> operation(DomainObjectOperation operation) {
            operations.put(operation.getName(), operation);
            return this;
        }

        private ImmutableObjectModel<T> build(ObjectRegistry registry) {

        }

    }

    private abstract class AbstractContextualBuilder<B extends AbstractContextualBuilder> {

        private final ImmutableContextPath path;
        private String description;
        private final Collection<ComponentAttribute> attributes
                = new ArrayList<>();
        private final Collection<ComponentConstraint> constraints
                = new ArrayList<>();

        public AbstractContextualBuilder(ImmutableContextPath path) {
            this.path = path;
        }

        public B description(String description) {
            this.description = description;
            return self();
        }

        public B attribute(ComponentAttribute attribute) {
            this.attributes.add(attribute);
            return self();
        }

        public B constraint(ComponentConstraint constraint) {
            this.constraints.add(constraint);
            return self();
        }

        abstract protected B self();
    }

    private class ContextualObjectModelBuilderImpl<T>
            extends AbstractContextualBuilder<ContextualObjectModelBuilderImpl<T>>
            implements ContextualObjectModelBuilder<T> {

        private final String baseComponentName;
        private ComplexObjectCodec<T> codecOverride;
        private final Set<String> ignoredOperation = new HashSet<>();
        private final Set<String> ignoredComposite = new HashSet<>();

        public ContextualObjectModelBuilderImpl(ImmutableContextPath path,
                String baseComponentName) {
            super(path);
            this.baseComponentName = baseComponentName;
        }

        @Override
        public ContextualObjectModelBuilderImpl<T> codec(ComplexObjectCodec<T> codec) {
            this.codecOverride = codec;
            return this;
        }

        @Override
        public ContextualObjectModelBuilderImpl<T> ignoreOperation(String operationName) {
            this.ignoredOperation.add(operationName);
            return this;
        }

        @Override
        public ContextualObjectModelBuilderImpl<T> ignoreComposite(String compositeName) {
            this.ignoredComposite.add(compositeName);
            return this;
        }

        @Override
        protected ContextualObjectModelBuilderImpl<T> self() {
            return this;
        }

        public ContextualObjectModel<T> build(ObjectRegistry registry) {

        }

    }

    private class ContextualObjectCollectionBuilderImpl<T>
            extends AbstractContextualBuilder<ContextualObjectCollectionBuilderImpl<T>>
            implements ContextualObjectCollectionBuilder<T> {

        private final AbstractContextualBuilder<T> containedModel;

        public ContextualObjectCollectionBuilderImpl(
                ImmutableCollectionPath collectionPath) {
            super(collectionPath);
            this.containedModel
                    = new ContextualObjectModelBuilderImpl(
                            collectionPath.(baseComponentName),
                            baseComponentName);
        }

        public static ContextualObjectCollectionBuilderImpl<?>
                collectionOfObjects(String baseComponentName) {

        }

        public static ContextualObjectCollectionBuilderImpl<?>
                collectionOfCollections() {

        }

        public static ContextualObjectCollectionBuilderImpl<?>
                collectionOfMaps() {

        }

        @Override
        protected ContextualObjectCollectionBuilderImpl<T> self() {
            return this;
        }

        @Override
        public ContextualObjectModelBuilder<T> getContainedModel() {
            return containedModel;
        }

        public ContextualCollectionModel<T> build(ObjectRegistry registry) {

        }

    }

    private class ContextualObjectMapBuilderImpl<K, V>
            extends AbstractContextualBuilder<ContextualObjectMapBuilderImpl<K, V>>
            implements ContextualObjectMapBuilder<K, V> {

        private final ContextualObjectModelBuilder<K> keyModelBuilder;
        private final ContextualObjectModelBuilder<V> valueModelBuilder;

        private ContextualObjectMapBuilderImpl(ImmutableMapPath mapsPath,
                String keyComponentName, String valueComponentName) {
            super(mapsPath);
            this.keyModelBuilder = new ContextualObjectModelBuilderImpl(
                    mapsPath.key(),
                    keyComponentName);
            this.valueModelBuilder = new ContextualObjectModelBuilderImpl(
                    mapsPath.value(),
                    valueComponentName);
        }

        @Override
        protected ContextualObjectMapBuilderImpl<K, V> self() {
            return this;
        }

        @Override
        public ContextualObjectModelBuilder<K> getKeyModel() {
            return keyModelBuilder;
        }

        @Override
        public ContextualObjectModelBuilder<V> getValueModel() {
            return valueModelBuilder;
        }

        public ObjectMapModel<K, V> build(ObjectRegistry registry) {

        }
    }

    /**
     * Domain model component registry used internally by the domain model and
     * model components.
     *
     * The InternalComponentRegistry instance itself is not immutable and
     * therefore should not be exposed externally without proper protections.
     *
     * This implementation is NOT thread-safe.
     */
    private class InternalObjectRegistry implements ObjectRegistry {

        private final String domainName;
        private final long domainVersion;
        private final Map<ModelContextPath, DomainObject<?>> domainComponents
                = new HashMap<>();
        private final Map<Class<? extends ComponentAttribute>, Collection<DomainObject<?>>> attributeIndex
                = new HashMap<>();
        //key=parent component path
        //value=component path which inherit from key
        private final Map<ModelContextPath, Set<ModelContextPath>> inheritenceIndex
                = new HashMap<>();

        public InternalObjectRegistry(String domainName, long domainVersion) {
            this.domainName = domainName;
            this.domainVersion = domainVersion;
        }

        private void register(DomainObject<?> object)
                throws ComponentCollisionException {
            if (domainComponents.containsKey(object.getPath())) {
                throw new ComponentCollisionException(
                        object.getPath(),
                        "Registration of component model failed, component "
                        + "already exists with this name in domain '"
                        + domainName + "-"
                        + String.valueOf(domainVersion) + "'");
            }

            domainComponents.put(object.getPath(), object);

            //add to attribute index, creating multi-map bucket as necessary
            object.getAttributes().stream()
                    .forEach((a) -> {
                        final Class<? extends ComponentAttribute> aClass
                        = a.getClass();
                        if (!attributeIndex.containsKey(aClass)) {
                            attributeIndex.put(aClass, new ArrayList<>());
                        }
                        attributeIndex.get(aClass).add(object);
                    });

            //add to inheritence index
            if (object instanceof InheritedObjectModel) {
                InheritedObjectModel<?> inheritedObject = (InheritedObjectModel) object;
                inheritedObject.inheritsFrom()
                        .stream() //parent components inherited from
                        .map((pc) -> pc.getPath())
                        .forEach((pn) -> {
                            //index key is parent component name
                            if (!inheritenceIndex.containsKey(pn)) {
                                inheritenceIndex.put(pn, new HashSet<>());
                            }
                            inheritenceIndex.get(pn).add(object.getPath());
                        });
            }

        }

        @Override
        public Collection<DomainObject<?>> findAll() {
            return Collections.unmodifiableCollection(
                    domainComponents.values()
            );
        }

        @Override
        public Optional<DomainObject<?>> findByName(String componentName) {
            return Optional.ofNullable(domainComponents.get(componentName));
        }

        @Override
        public Collection<DomainObject<?>> findByAttribute(
                Class<? extends ComponentAttribute> attributeType) {

            if (!attributeIndex.containsKey(attributeType)) {
                //no components with that attribute
                return Collections.EMPTY_LIST;
            }

            return Collections.unmodifiableCollection(
                    attributeIndex.get(attributeType)
            );
        }

        @Override
        public Collection<DomainObject<?>> findSpecialized(
                String parentComponentName) {
            if (!inheritenceIndex.containsKey(parentComponentName)) {
                return Collections.EMPTY_LIST;
            }

            return Collections.unmodifiableCollection(
                    inheritenceIndex.get(parentComponentName)
                    .stream()
                    .map((cn) -> domainComponents.get(cn))
                    .collect(Collectors.toCollection(null)));
        }

    }
}
