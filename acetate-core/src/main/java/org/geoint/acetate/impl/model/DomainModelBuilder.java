package org.geoint.acetate.impl.model;

import java.lang.reflect.Method;
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
import org.geoint.acetate.codec.ObjectCodec;
import org.geoint.acetate.impl.codec.DefaultBooleanCodec;
import org.geoint.acetate.impl.codec.DefaultIntegerCodec;
import org.geoint.acetate.impl.codec.DefaultStringCodec;
import org.geoint.acetate.model.constraint.ComponentConstraint;
import org.geoint.acetate.model.ComponentModel;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.registry.ComponentRegistry;

/**
 * API to programmatically build a DomainModel.
 *
 */
public class DomainModelBuilder {

    private final String name;
    private final long version;
    private String description;
    private Map<String, ComponentModelBuilderImpl<?>> components;
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
     */
    public static DomainModelBuilder newDefault(String modelName,
            long modelVersion) {
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
    public ComponentModelBuilder<?> component(String componentName) {
        return component(componentName, null);
    }

    /**
     * Add a component associated with a known java data type.
     *
     * @param <T> data type
     * @param componentName domain-unique component name
     * @param componentType data type of the component model
     * @return builder for this component model
     */
    public <T> ComponentModelBuilder<T> component(String componentName,
            Class<T> componentType) {
        if (components.containsKey(componentName)) {
            components.put(componentName,
                    new ComponentModelBuilderImpl(componentName, componentType));
        }
        return (ComponentModelBuilder<T>) components.get(componentName);
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

        final InternalComponentRegistry componentRegistry
                = new InternalComponentRegistry(this.name, this.version);

        for (Entry<String, ComponentModelBuilderImpl<?>> e
                : components.entrySet()) {
            //for each component
            componentRegistry.register(buildComponent(e.getValue()));
        }

        return new ImmutableDomainModel(componentRegistry,
                DomainUtil.uniqueDomainId(name, version),
                this.name, this.version);
    }

    private <T> ComponentModel<T> buildComponent(ComponentModelBuilderImpl<T> cb)
            throws IncompleteModelException {

        final String componentName = cb.componentName;

        if (cb.compositeComponents.isEmpty()) {
            //value component (is not composite)
            return new ImmutableComponentModel();
        } else {
            //composite component
            
            for (Entry<String, ComponentDependent> ce
                    : cb.compositeComponents.entrySet()) {

                final String compositeLocalName = ce.getKey();
                final ComponentDependent compositeContext = ce.getValue();

                for (String compositeComponentName : compositeContext.getComponentDependencies()) {
                    if (!components.containsKey(compositeComponentName)) {
                        // ensure that all component dependencies have been 
                        //registered with the domain model (even it it hasn't been 
                        //built yet)
                        throw new IncompleteModelException(this.name,
                                this.version,
                                "Composite component '" + componentName
                                + "' depends on a component named '"
                                + compositeComponentName + "' (locally named '"
                                + compositeLocalName
                                + "') which could not be found "
                                + "in the domain model.");
                    }
                }

            }
            return new CompositeComponentModelImpl();
        }
    }

    private static DomainModel defaultModel()
            throws ComponentCollisionException {
        if (DEFAULT_MODEL == null) {
            DomainModelBuilder db = new DomainModelBuilder("DEFAULT", 1);
            db.component("java.util.String", String.class)
                    .codec(new DefaultStringCodec());
            db.component("java.lang.Integer", Integer.class)
                    .codec(new DefaultIntegerCodec());
            db.component("java.lang.Boolean", Boolean.class)
                    .codec(new DefaultBooleanCodec());

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

    private class ComponentModelBuilderImpl<T>
            implements ComponentModelBuilder<T>, ComponentDependent {

        private final String componentName;
        private final Class<T> componentType;
        private ObjectCodec<T> codec;
        private final Set<ComponentConstraint> constraints = new HashSet<>();
        private final Set<ComponentAttribute> attributes = new HashSet<>();
        //key = local name, value = composite component name
        private final Map<String, ComponentDependent> compositeComponents
                = new TreeMap<>();
        private final Set<String> inheritedComponents = new HashSet<>();
        private final Map<String, ComponentOperationBuilderImpl> operations
                = new HashMap<>();

        public ComponentModelBuilderImpl(String componentName) {
            this.componentName = componentName;
            this.componentType = null;
        }

        public ComponentModelBuilderImpl(String componentName,
                Class<T> componentType) {
            this.componentName = componentName;
            this.componentType = componentType;
        }

        @Override
        public ComponentModelBuilder<T> constraint(
                ComponentConstraint constraint) {
            constraints.add(constraint);
            return this;
        }

        @Override
        public ComponentModelBuilder<T> attribute(ComponentAttribute attribute) {
            this.attributes.add(attribute);
            return this;
        }

        @Override
        public ComponentModelBuilder<T> codec(ObjectCodec<T> codec) {
            this.codec = codec;
            return this;
        }

        @Override
        public ContextBuilder<?> composite(String localName,
                String componentName) {
            return composite(localName,
                    new ValueContextBuilderImpl(componentName));
        }

        @Override
        public ContextBuilder<?> compositeCollection(String localName,
                String componentName) {
            return composite(localName,
                    new CollectionContextBuilderImpl(componentName));
        }

        @Override
        public MapContextBuilder<?, ?> compositeMap(String localName,
                String keyComponentName, String valueComponentName) {
            return composite(localName,
                    new MapContextBuilderImpl(keyComponentName,
                            valueComponentName));
        }

        private <B extends ComponentDependent> B composite(String localName,
                B ref) {
            compositeComponents.put(localName, ref);
            return ref;
        }

        @Override
        public ComponentModelBuilder specializes(String parentComponentName) {
            inheritedComponents.add(parentComponentName);
            return this;
        }

        @Override
        public ComponentOperationBuilder operation(String name) {
            ComponentOperationBuilderImpl ob
                    = new ComponentOperationBuilderImpl(name);
            operations.put(name, ob);
            return ob;
        }

        @Override
        public void appendDependencies(Set<String> appendable) {
            this.compositeComponents.values().stream()
                    .forEach((v) -> v.appendDependencies(appendable));
            this.operations.values().stream()
                    .forEach((v) -> v.appendDependencies(appendable));
            appendable.addAll(this.inheritedComponents);
        }

    }

    private class ComponentOperationBuilderImpl
            implements ComponentOperationBuilder,
            ComponentDependent {

        private final String operationName;
        private String description;
        private Method method;
        private ComponentDependent returnComponent;
        private Map<String, ComponentDependent> parameters;

        public ComponentOperationBuilderImpl(String operationName) {
            this.operationName = operationName;
        }

        @Override
        public ComponentOperationBuilder description(String description) {
            this.description = description;
            return this;
        }

        @Override
        public ComponentOperationBuilder method(Method m) {
            this.method = m;
            return this;
        }

        @Override
        public ContextBuilder<?> returns(String componentName) {
            returnComponent
                    = new ValueContextBuilderImpl(componentName);
            return (ContextBuilder<?>) returnComponent;
        }

        @Override
        public ContextBuilder<?> returnsCollection(String componentName) {
            returnComponent
                    = new CollectionContextBuilderImpl(componentName);
            return (ContextBuilder<?>) returnComponent;
        }

        @Override
        public MapContextBuilder<?, ?> returnsMap(String keyComponentName, String valueComponentName) {
            returnComponent
                    = new MapContextBuilderImpl(keyComponentName,
                            valueComponentName);
            return (MapContextBuilder<?, ?>) returnComponent;
        }

        @Override
        public ContextBuilder<?> parameter(String componentName,
                String paramName) {
            parameters.put(paramName, new ValueContextBuilderImpl(
                    componentName));
            return (ContextBuilder<?>) parameters.get(paramName);
        }

        @Override
        public ContextBuilder<?> collectionParameter(
                String componentName, String paramName) {
            parameters.put(paramName, new CollectionContextBuilderImpl(
                    componentName));
            return (ContextBuilder<?>) parameters.get(paramName);
        }

        @Override
        public ContextBuilder<?> mapParameter(String keyComponentName,
                String valueComponentName, String paramName) {
            parameters.put(paramName, new MapContextBuilderImpl(
                    keyComponentName, valueComponentName));
            return (ContextBuilder<?>) parameters.get(paramName);
        }

        @Override
        public void appendDependencies(Set<String> appendable) {
            returnComponent.appendDependencies(appendable);
            parameters.entrySet().stream()
                    .map((e) -> e.getValue())
                    .forEach((d) -> d.appendDependencies(appendable)
                    );
        }

    }

    /**
     * Model component which may depends on one or more model components.
     */
    @FunctionalInterface
    private interface ComponentDependent {

        /**
         * Returns a list of components this component depends on (including
         * transitive dependencies).
         *
         * @return component dependencies
         */
        default Set<String> getComponentDependencies() {
            Set<String> dependencies = new HashSet<>();
            appendDependencies(dependencies);
            return dependencies;
        }

        /**
         * Adds component names gathered from the collection of dependents to
         * the provided 'appendable' collection of component names.
         *
         * @param appendable set of component names to add to
         * @param dependents components which may depend on other components
         */
        void appendDependencies(Set<String> appendable);
    }

    private class ValueContextBuilderImpl<T>
            implements ComponentDependent,
            ContextBuilder<T> {

        private final String componentName;
        private ObjectCodec<T> codec;
        private Collection<ComponentAttribute> attributes
                = new ArrayList<>();
        private Collection<ComponentConstraint> constraints
                = new ArrayList<>();

        public ValueContextBuilderImpl(String componentName) {
            this.componentName = componentName;
        }

        @Override
        public ContextBuilder<T> codec(ObjectCodec<T> codec) {
            this.codec = codec;
            return this;
        }

        @Override
        public ContextBuilder<T> attribute(ComponentAttribute attribute) {
            this.attributes.add(attribute);
            return this;
        }

        @Override
        public ContextBuilder<T> constraint(
                ComponentConstraint constraint) {
            this.constraints.add(constraint);
            return this;
        }

        @Override
        public void appendDependencies(Set<String> appendable) {
            appendable.add(componentName);
        }
    }

    private class CollectionContextBuilderImpl<T>
            extends ValueContextBuilderImpl<T> {

        public CollectionContextBuilderImpl(String componentName) {
            super(componentName);
        }

    }

    private class MapContextBuilderImpl<K, V>
            implements ComponentDependent,
            MapContextBuilder<K, V> {

        private final String keyComponentName;
        private ObjectCodec<K> keyCodec;
        private final Collection<ComponentAttribute> keyAttributes
                = new ArrayList<>();
        private final Collection<ComponentConstraint> keyConstraints
                = new ArrayList<>();
        private final String valueComponentName;
        private ObjectCodec<V> valueCodec;
        private final Collection<ComponentAttribute> valueAttributes
                = new ArrayList<>();
        private final Collection<ComponentConstraint> valueConstraints
                = new ArrayList<>();

        private MapContextBuilderImpl(String keyComponentName,
                String valueComponentName) {
            this.keyComponentName = keyComponentName;
            this.valueComponentName = valueComponentName;
        }

        @Override
        public MapContextBuilder<K, V> keyCodec(ObjectCodec<K> codec) {
            this.keyCodec = codec;
            return this;
        }

        @Override
        public MapContextBuilder<K, V> keyAttribute(ComponentAttribute attribute) {
            this.keyAttributes.add(attribute);
            return this;
        }

        @Override
        public MapContextBuilder<K, V> keyConstraint(ComponentConstraint constraint) {
            this.keyConstraints.add(constraint);
            return this;
        }

        @Override
        public MapContextBuilder<K, V> valueCodec(ObjectCodec<V> codec) {
            this.valueCodec = codec;
            return this;
        }

        @Override
        public MapContextBuilder<K, V> valueAttribute(ComponentAttribute attribute) {
            this.valueAttributes.add(attribute);
            return this;
        }

        @Override
        public MapContextBuilder<K, V> valueConstraint(ComponentConstraint constraint) {
            this.valueConstraints.add(constraint);
            return this;
        }

        @Override
        public void appendDependencies(Set<String> appendable) {
            appendable.add(keyComponentName);
            appendable.add(valueComponentName);
        }
    }

    /**
     * Domain model component registry used internally by the domain model and
     * model components.
     *
     * The InternalComponentRegistry instance is not immutable and therefore
     * must not be exposed externally without proper protections.
     *
     * This implementation is NOT thread-safe.
     */
    private class InternalComponentRegistry implements ComponentRegistry {

        private final String domainName;
        private final long domainVersion;
        private final Map<String, ComponentModel<?>> domainComponents
                = new HashMap<>();
        private final Map<Class<? extends ComponentAttribute>, Collection<ComponentModel<?>>> attributeIndex
                = new HashMap<>();
        //key=parent component name
        //value=component names which inherit from key
        private final Map<String, Set<String>> inheritenceIndex
                = new HashMap<>();

        public InternalComponentRegistry(String domainName, long domainVersion) {
            this.domainName = domainName;
            this.domainVersion = domainVersion;
        }

        private void register(ComponentModel<?> component)
                throws ComponentCollisionException {
            final String cn = component.getComponentName();
            if (domainComponents.containsKey(cn)) {
                throw new ComponentCollisionException(domainName, domainVersion,
                        domainComponents.get(cn), component,
                        "Registration of component model failed, component already "
                        + "exists with this name in domain '"
                        + domainName + "-" + String.valueOf(domainVersion) + "'");
            }

            domainComponents.put(cn, component);

            //add to attribute index, creating multi-map bucket as necessary
            component.getContext().getAttributes().stream()
                    .forEach((a) -> {
                        final Class<? extends ComponentAttribute> aClass
                        = a.getClass();
                        if (!attributeIndex.containsKey(aClass)) {
                            attributeIndex.put(aClass, new ArrayList<>());
                        }
                        attributeIndex.get(aClass).add(component);
                    });

            //add to inheritence index
            component.getInheritedComponents()
                    .stream() //parent components inherited from
                    .map((pc) -> pc.getComponentName())
                    .forEach((pn) -> {
                        //index key is parent component name
                        if (!inheritenceIndex.containsKey(pn)) {
                            inheritenceIndex.put(pn, new HashSet<>());
                        }
                        inheritenceIndex.get(pn).add(cn);
                    });
        }

        @Override
        public Optional<ComponentModel<?>> findByName(String componentName) {
            return Optional.ofNullable(domainComponents.get(componentName));
        }

        @Override
        public Collection<ComponentModel<?>> findByAttribute(
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
        public Collection<ComponentModel<?>> findSpecialized(
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
