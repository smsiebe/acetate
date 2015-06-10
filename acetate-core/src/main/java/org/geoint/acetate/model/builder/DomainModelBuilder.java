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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.geoint.acetate.impl.model.DomainModelImpl;
import org.geoint.acetate.impl.model.DomainUtil;
import org.geoint.acetate.impl.model.ImmutableComponentAddress;
import org.geoint.acetate.impl.transform.DefaultBooleanBinaryCodec;
import org.geoint.acetate.impl.transform.DefaultIntegerBinaryCodec;
import org.geoint.acetate.impl.transform.DefaultStringBinaryCodec;
import org.geoint.acetate.model.address.ComponentAddress;
import org.geoint.acetate.model.DomainObject;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.ModelException;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import gov.ic.geoint.acetate.ObjectRegistry;

/**
 * API to programmatically build a DomainModel.
 *
 */
public class DomainModelBuilder {

    private final String name;
    private final long version;
    private String displayName;
    private String description;
    private Map<String, DomainObjectBuilder<?>> components;
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

    public DomainModelBuilder withDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    /**
     * Add a component to the domain model.
     *
     * @param objectName domain-unique object name
     * @return builder for this object model
     */
    public DomainObjectBuilder<?> object(String objectName) {
        return object(objectName);
    }

    public <T> DomainObjectBuilder<T> object(String objectName, Class<T> type) {
        if (components.containsKey(objectName)) {
            components.put(objectName,
                    new DomainObjectBuilder(
                            ImmutableComponentAddress.basePath(name,
                                    version, objectName)
                    ));
        }
        return (DomainObjectBuilder<T>) components.get(objectName);
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
    public DomainModel build() throws IncompleteModelException,
            ComponentCollisionException {

        final InternalObjectRegistry registry = new InternalObjectRegistry();
        final DomainModelImpl model = new DomainModelImpl(
                DomainUtil.uniqueDomainId(name, version),
                registry, name, version, displayName, description);

        //first ask the builders for all the domain component names they 
        //depend on, so we can make sure they exist
        Set<String> missingComponents = components.entrySet().stream()
                .map((e) -> e.getValue())
                .flatMap((d) -> d.getDependencies().stream())
                .filter((c) -> !components.containsKey(c))
                .collect(Collectors.toSet());
        if (!missingComponents.isEmpty()) {
            throw new IncompleteModelException(name, version,
                    "Unable to build domain model, "
                    + "the following object components could not be found: "
                    + missingComponents.stream().collect(
                            Collectors.joining(",", "[", "]")
                    ));
        }

        //iterate through the object builders, telling them to build themselves.
        //builders will recursively build their models, creating deferred 
        //objects if the component isn't yet available
        for (Entry<String, DomainObjectBuilder<?>> e : components.entrySet()) {
            final String objectName = e.getKey();
            final DomainObjectBuilder<?> ob = e.getValue();
            registry.register(ob.build(model));
        }

        return model;
    }

    private static DomainModel defaultModel()
            throws ComponentCollisionException {
        if (DEFAULT_MODEL == null) {
            DomainModelBuilder db = new DomainModelBuilder("DEFAULT", 1);
            db.object("string", String.class)
                    .codec(new DefaultStringBinaryCodec());
            db.object("int", Integer.class)
                    .codec(new DefaultIntegerBinaryCodec());
            db.object("boolean", Boolean.class)
                    .codec(new DefaultBooleanBinaryCodec());

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

        private final Map<ComponentAddress, DomainObject<?>> domainComponents
                = new HashMap<>();
        private final Map<Class<? extends ComponentAttribute>, Collection<DomainObject<?>>> attributeIndex
                = new HashMap<>();
        //key=parent component path
        //value=component path which inherit from key
        private final Map<ComponentAddress, Set<ComponentAddress>> inheritenceIndex
                = new HashMap<>();

        private void register(DomainObject<?> object)
                throws ComponentCollisionException {
            if (domainComponents.containsKey(object.getPath())) {
                throw new ComponentCollisionException(
                        object.getPath(),
                        "Registration of component model failed, component "
                        + "already exists with this name in domain '"
                        + name + "-"
                        + String.valueOf(version) + "'");
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
            if (!object.getParentObjectNames().isEmpty()) {
                object.getParentObjectNames()
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
