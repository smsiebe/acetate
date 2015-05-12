package org.geoint.acetate.impl.model;

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
import java.util.stream.Collectors;
import org.geoint.acetate.codec.ObjectCodec;
import org.geoint.acetate.impl.codec.DefaultBooleanCodec;
import org.geoint.acetate.impl.codec.DefaultIntegerCodec;
import org.geoint.acetate.impl.codec.DefaultStringCodec;
import static org.geoint.acetate.impl.model.DomainModelBuilder.defaultModel;
import org.geoint.acetate.model.CollectionCompositeComponent;
import org.geoint.acetate.model.constraint.ComponentConstraint;
import org.geoint.acetate.model.ComponentModel;
import org.geoint.acetate.model.CompositeComponent;
import org.geoint.acetate.model.CompositeComponentModel;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.MapCompositeComponent;
import org.geoint.acetate.model.SimpleCompositeComponent;
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
     */
    public DomainModel build() throws IncompleteModelException {

        final ComponentRegistry componentRegistry
                = new InternalComponentRegistry(this.name, this.version);

        for (Entry<String, ComponentModelBuilderImpl<?>> e
                : components.entrySet()) {
            //for each component
            ComponentModel<?> cm;
            final String componentName = e.getKey();
            final ComponentModelBuilderImpl<?> cb = e.getValue();

            if (cb.compositeComponents.isEmpty()) {
                //value component (is not composite)
                cm = new ComponentModelImpl();
            } else {
                //composite component
                for (Entry<String, String> ce
                        : cb.compositeComponents.entrySet()) {

                    final String compositeLocalName = ce.getKey();
                    final String compositeComponentName = ce.getValue();

                    // ensure that composite component is registered with the 
                    //domain model
                    if (!components.containsKey(compositeComponentName)) {
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
                cm = new CompositeComponentModelImpl();
            }

        }
    }

    public static DomainModel defaultModel() {
        if (DEFAULT_MODEL == null) {
            DomainModelBuilder db = new DomainModelBuilder("DEFAULT", 1);
            db.component("java.util.String", String.class)
                    .codec(new DefaultStringCodec());
            db.component("java.lang.Integer", Integer.class)
                    .codec(new DefaultIntegerCodec());
            db.component("java.lang.Boolean", Boolean.class)
                    .codec(new DefaultBooleanCodec());

            //TODO add the rest
            DEFAULT_MODEL = db.build();
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

    private class ComponentModelImpl<T> implements ComponentModel<T> {

        private final String name;
        private final Optional<Class<T>> type;
        private final Collection<ComponentConstraint> constraints;
        private final Collection<ComponentAttribute> attributes;
        private final Collection<ComponentModel<?>> parentComponents;

        /**
         *
         * @param name domain-unique component name
         * @param type component type (may be null)
         * @param constraints component constraints
         * @param attributes component attributes
         */
        private ComponentModelImpl(String name, Class<T> type,
                Collection<ComponentConstraint> constraints,
                Collection<ComponentAttribute> attributes,
                Collection<ComponentModel<?>> parentComponents) {
            this.name = name;
            this.type = Optional.ofNullable(type);
            this.constraints = Collections.unmodifiableCollection(constraints);
            this.attributes = Collections.unmodifiableCollection(attributes);
            this.parentComponents = Collections.unmodifiableCollection(parentComponents);
        }

        @Override
        public String getComponentName() {
            return name;
        }

        @Override
        public Optional<Class<T>> getDataType() {
            return type;
        }

        @Override
        public Collection<ComponentConstraint> getConstraints() {
            return constraints;
        }

        @Override
        public Collection<ComponentAttribute> getAttributes() {
            return attributes;
        }

        @Override
        public Collection<ComponentModel<?>> getInheritedComponents() {
            return parentComponents;
        }

    }

    private class CompositeComponentModelImpl<T> extends ComponentModelImpl<T>
            implements CompositeComponentModel<T> {

        //key is local name, value is domain-unique composite name
        private final Map<String, CompositeComponentRef> composites;

        public CompositeComponentModelImpl(String name, Class<T> type,
                Collection<ComponentConstraint> constraints,
                Collection<ComponentAttribute> attributes,
                Collection<ComponentModel<?>> parentComponents,
                Map<String, CompositeComponentRef> composites) {
            super(name, type, constraints, attributes, parentComponents);
            this.composites = composites;
        }

        @Override
        public Set<String> getCompositeNames() {
            return Collections.unmodifiableSet(composites.keySet());
        }

        @Override
        public Collection<CompositeComponent> getComposites() {
            return Collections.unmodifiableCollection(composites.values());
        }

    }

    private class ComponentModelBuilderImpl<T> implements ComponentModelBuilder<T> {

        private final String componentName;
        private final Class<T> componentType;
        private ObjectCodec<T> codec;
        private final Set<ComponentConstraint> constraints = new HashSet<>();
        private final Set<ComponentAttribute> attributes = new HashSet<>();
        //key = local name, value = composite component name
        private final Map<String, CompositeComponentRef> compositeComponents
                = new TreeMap<>();

        public ComponentModelBuilderImpl(String componentName) {
            this.componentName = componentName;
            this.componentType = null;
        }

        public ComponentModelBuilderImpl(String componentName, Class<T> componentType) {
            this.componentName = componentName;
            this.componentType = componentType;
        }

        @Override
        public ComponentModelBuilder<T> constraint(ComponentConstraint constraint) {
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
        public ComponentModelBuilder<T> composite(String localName,
                String componentName) {
            compositeComponents.put(localName,
                    new SimpleCompositeComponentRef(localName, componentName));
            return this;
        }

        @Override
        public ComponentModelBuilder compositeCollection(String localName, String componentName) {

        }

        @Override
        public ComponentModelBuilder compositeMap(String localName, String keyComponentName, String valueComponentName) {

        }
    }

    /**
     * Implements maintain a reference (component name) to composite component
     * models, so requires access to the ComponentRegistry to resolve.
     */
    private abstract class CompositeComponentRef implements CompositeComponent {

        private final ComponentRegistry registry;
        private final String localName;

        public CompositeComponentRef(ComponentRegistry registry,
                String localName) {
            this.localName = localName;
            this.registry = registry;
        }

        @Override
        public String getLocalName() {
            return localName;
        }

        protected ComponentModel<?> resolveComponentModel(String componentName) {
            Optional<ComponentModel<?>> cm = registry.findByName(componentName);

            //component model is expected in the registry, it is checked during 
            //the domain model build process
            assert cm.isPresent() : "Component not present in model registry!";

            return cm.get();
        }

    }

    private class SimpleCompositeComponentRef extends CompositeComponentRef
            implements SimpleCompositeComponent {

        private final String componentName;

        public SimpleCompositeComponentRef(ComponentRegistry registry,
                String localName,
                String componentName) {
            super(registry, localName);
            this.componentName = componentName;
        }

        @Override
        public ComponentModel getComponentModel() {
            return resolveComponentModel(componentName);
        }
    }

    private class CollectionCompositeComponentRef extends CompositeComponentRef
            implements CollectionCompositeComponent {

        private final String componentName;

        public CollectionCompositeComponentRef(ComponentRegistry registry,
                String localName, String componentName) {
            super(registry, localName);
            this.componentName = componentName;
        }

        @Override
        public ComponentModel getComponentModel() {
            return resolveComponentModel(componentName);
        }
    }

    private class MapCompositeComponentRef extends CompositeComponentRef
            implements MapCompositeComponent {

        private final String keyComponentName;
        private final String valueComponentName;

        public MapCompositeComponentRef(ComponentRegistry registry,
                String localName, String keyComponentName,
                String valueComponentName) {
            super(registry, localName);
            this.keyComponentName = keyComponentName;
            this.valueComponentName = valueComponentName;
        }

        @Override
        public ComponentModel getKeyModel() {
            return resolveComponentModel(keyComponentName);
        }

        @Override
        public ComponentModel getValueModel() {
            return resolveComponentModel(valueComponentName);
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
            component.getAttributes().stream()
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
