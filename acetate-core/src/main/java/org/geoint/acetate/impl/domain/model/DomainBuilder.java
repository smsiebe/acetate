package org.geoint.acetate.impl.domain.model;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.geoint.acetate.domain.model.DomainModel;
import org.geoint.acetate.domain.model.ObjectModel;
import org.geoint.acetate.domain.model.OperationModel;
import org.geoint.acetate.domain.model.ParameterModel;
import org.geoint.acetate.meta.MetaVersion;
import org.geoint.acetate.meta.ModelException;

/**
 * Fluid API to programmatically create one or more ObjectModel instances.
 *
 * The builder API is thread safe.
 */
public final class DomainBuilder {

    //builders 
    private final Map<ObjectId, WeakReference<ObjectBuilderImpl>> objects
            = new WeakHashMap<>();
    //completed models, empty until #build() is called but needed here 
    //for inner class visibility
    private final Map<ObjectId, ObjectModel> objectModels = new HashMap<>();

    private static final Logger logger
            = Logger.getLogger(DomainBuilder.class.getName());

    /**
     * Add an object model to the domain.
     *
     * @param model
     * @return this builder
     */
    public DomainBuilder withObject(ObjectModel model) {
        objectModels.put(
                ObjectId.getInstance(model.getDomainName(),
                        model.getDomainVersion(),
                        model.getName()),
                model);
        return this;
    }

    /**
     * Return ObjectModelBuilder to programmatically create an ObjectModel for a
     * java class.
     *
     * @param domainName
     * @param domainVersion
     * @param objectName
     * @return object model builder
     */
    public ObjectModelBuilder forObject(String domainName,
            MetaVersion domainVersion, String objectName) {
        return registerObjectType(
                ObjectId.getInstance(domainName, domainVersion, objectName)
        );
    }

    public ObjectModelBuilder forObject(ObjectId domainId) {
        return registerObjectType(domainId);
    }

    /**
     * Creates and caches a new ObjectModel builder if one does not already
     * exist for this class.
     *
     * @param objectName
     */
    private ObjectBuilderImpl registerObjectType(ObjectId domainId) {
        synchronized (objects) {
            //register parent class model builder if it hasn't been already
            if (!objects.containsKey(domainId)) {
                ObjectBuilderImpl builder = new ObjectBuilderImpl(domainId);
                objects.put(domainId, new WeakReference(builder));
                return builder;
            }
        }
        return objects.get(domainId).get();
    }

    /**
     * Build the object models.
     *
     * Returns all the ObjectModel instances created with this builder, throwing
     * a ModelException if the models do not meet the following validity
     * requirements:
     *
     * <ul>
     * <li>All models contain values, default or explicitly set, for all
     * required attributed.</li>
     * <li>All ObjectModel dependencies (ie referencing other object models) are
     * present.</li>
     * </ul>
     *
     * @return object models created with this builder
     * @throws ModelException thrown if there is an invalid model
     */
    public Collection<DomainModel> build()
            throws ModelException {

        //validate this.objects and creates models, adding them to this.objectModels
        buildObjectModels();

        //build domain models from this.objectModels
        final Map<DomainId, DomainModel> domainModels = buildDomainModels();

        //ensure builder instances are eligible for GC from this builder
        objects.clear();
        objectModels.clear();

        return domainModels.values();
    }

    //validate this.objects and creates models, adding them to this.objectModels
    private void buildObjectModels() throws ModelException {

        for (WeakReference<ObjectBuilderImpl> oref : objects.values()) {
            final ObjectBuilderImpl ob = oref.get();
            final ObjectId id = ob.objectId;

            //ask each object model builder to validate itself.  since each 
            //object references (ie operation param/return/exception) is 
            //registered, they will throw an exception if they are not fully 
            //modeled 
            ob.validate();
        }

        modelDeferringDeadlocks(objects.keySet());
    }

    private Set<ObjectModel> modelDeferringDeadlocks(
            Collection<ObjectId> objectIds) throws ModelException {
        Set<ObjectModel> om = new HashSet<>();
        for (ObjectId oid : objectIds) {
            if (objectModels.containsKey(oid)) {
                om.add(objectModels.get(oid));
                continue;
            }
            om.add(modelDeferringDeadlocks(oid));
        }
        return om;
    }

    private ObjectModel modelDeferringDeadlocks(ObjectId oid)
            throws ModelException {
        //defer this object modeling...then kick it off...neat trick to 
        //prevent any circular dependencies
        DeferredImmutableObjectModel dom
                = new DeferredImmutableObjectModel(oid, objects.get(oid).get());
        objectModels.put(oid, dom);
        dom.execute();
        return dom;
    }

    private Set<OperationModel> modelDeferringDeadlocks(
            Map<String, OperationBuilderImpl> opBuilders) throws ModelException {
        Set<OperationModel> ops = new HashSet<>();
        for (OperationBuilderImpl opb : opBuilders.values()) {
            Map<String, ParameterModel> params = new HashMap<>();
            for (Entry<String, ObjectId> pe : opb.parameters.entrySet()) {
                final String paramName = pe.getKey();
                params.put(paramName,
                        new ImmutableParameterModel(paramName,
                                modelDeferringDeadlocks(pe.getValue())
                        ));
            }
            ops.add(new ImmutableOperationModel(
                    opb.operationName,
                    params,
                    new ImmutableReturnModel(
                            (opb.returnType != null)
                                    ? modelDeferringDeadlocks(opb.returnType)
                                    : null,
                            opb.exceptions.stream()
                            .map((oid) -> new ImmutableThrowableModel(oid))
                            .collect(Collectors.toSet())
                    )
            ));
        }
        return ops;
    }

    //build domain models from this.objectModels
    private Map<DomainId, DomainModel> buildDomainModels() throws ModelException {
        final Map<DomainId, Set<ObjectModel>> domainObjects = new HashMap<>();
        final Map<DomainId, Set<DomainId>> ontologies = new HashMap<>(); //key present if domain is an ontology, with the value containing all external domains 

        for (Entry<ObjectId, ObjectModel> e : this.objectModels.entrySet()) {
            final ObjectModel model = e.getValue();
            final ObjectId objectId = e.getKey();
            final DomainId domainId = objectId.getDomainId();

            //add to domain objects list
            domainObjects.putIfAbsent(domainId, new HashSet<>()); //yuk...method reference?
            domainObjects.get(domainId).add(model);

            //find out if it uses object from any external domains from the 
            //object builder, which kept track 
            Set<DomainId> relatedDomains = objects.get(objectId).get().getRelatedDomains();
            if (!relatedDomains.isEmpty()) {
                ontologies.putIfAbsent(domainId, new HashSet<>());
                ontologies.get(domainId).addAll(relatedDomains);
            }
        }

        //create domain or ontology model(s)
        final Map<DomainId, DomainModel> domains = new HashMap<>();
        for (Entry<DomainId, Set<ObjectModel>> e : domainObjects.entrySet()) {
            final DomainId domainId = e.getKey();
            if (ontologies.containsKey(domainId)) {
                domains.put(domainId,
                        buildOntology(domainId,
                                Stream.concat(e.getValue().stream(), //ontology-local
                                        ontologies.get(domainId).stream()
                                        .flatMap((id)
                                                //plus all object of related domains
                                                -> domainObjects.get(id).stream()
                                        )
                                )
                                .collect(Collectors.toSet())));
            } else {
                domains.put(domainId, buildDomain(domainId, e.getValue()));
            }
        }
        return domains;
    }

    private ImmutableOntology buildOntology(DomainId domainId,
            Set<ObjectModel> allDomainObjects) {
        return ImmutableOntology.fromObjects(domainId, allDomainObjects);
    }

    private ImmutableDomainModel buildDomain(DomainId domainId,
            Set<ObjectModel> objects) {
        return new ImmutableDomainModel(domainId, objects);
    }

    /**
     * Validates that all passed object Ids are registered with the object
     * cache.
     *
     * @param objectIds
     * @throws UnknownDomainObjectException if any objectId is not registered
     */
    private void validateRegistered(Collection<ObjectId> objectIds)
            throws UnknownDomainObjectException {
        for (ObjectId id : objectIds) {
            if (!objects.containsKey(id)) {
                throw new UnknownDomainObjectException(id, "Unknown "
                        + "domain object '" + id.asString() + "'");
            }
        }
    }

    private class ObjectBuilderImpl implements ObjectModelBuilder {

        private final ObjectId objectId;
        private final Map<String, String> attributes
                = new ConcurrentHashMap<>();
        private final Map<String, OperationBuilderImpl> operations
                = new HashMap<>();
        private final Set<ObjectId> parents
                = Collections.synchronizedSet(new HashSet<>());
        private final Set<ObjectId> specializations
                = Collections.synchronizedSet(new HashSet<>());
        //used to keep track of related domain models
        private final Set<DomainId> relatedDomains
                = Collections.synchronizedSet(new HashSet<>());

        public ObjectBuilderImpl(String domainName,
                MetaVersion domainVersion,
                String objectName) {
            this(ObjectId.getInstance(domainName, domainVersion, objectName));
        }

        public ObjectBuilderImpl(ObjectId objectId) {
            this.objectId = objectId;
        }

        @Override
        public ObjectId getObjectId() {
            return objectId;
        }

        @Override
        public ObjectModelBuilder specializes(ObjectId... parentId) {
            for (ObjectId pid : parentId) {
                specialize(pid);
            }
            return this;
        }

        @Override
        public ObjectModelBuilder specializes(String... parentObjectNames) {
            Arrays.stream(parentObjectNames)
                    .map((pn) -> ObjectId.getInstance(this.objectId.getDomainId(), pn))
                    .forEach((oid) -> specialize(oid));
            return this;
        }

        private void specialize(ObjectId parentId) {

            trackRelated(parentId);

            final ObjectBuilderImpl parentClassBuilder
                    = registerObjectType(parentId);
            //tell the parent model builder that this object specializes it
            parentClassBuilder.specializations.add(this.objectId);

            //tell this model about the parent object model
            this.parents.add(parentId);
        }

        @Override
        public ObjectModelBuilder withAttribute(String name, String value) {
            this.attributes.put(name, value);
            return this;
        }

        @Override
        public ObjectModelBuilder withAttributes(
                Map<String, String> metamodelAttributes) {
            metamodelAttributes.entrySet()
                    .forEach((e) -> withAttribute(e.getKey(), e.getValue()));
            return this;
        }

        @Override
        public OperationModelBuilder withOperation(String operationName) {
            //return existing operation builder, if exists
            synchronized (operations) {
                if (operations.containsKey(operationName)) {
                    return operations.get(operationName);
                }
                OperationBuilderImpl opBuilder
                        = new OperationBuilderImpl(this, operationName);
                operations.put(operationName, opBuilder);
                return opBuilder;
            }
        }

        private void validate() throws ModelException {
            //validate itself 
            for (ObjectId s : specializations) {
                //verfiy there aren't any circular dependencies 
                if (this.parents.contains(s)) {
                    throw new ModelInheritanceException(this.objectId,
                            "Specialized class '" + s.asString() + "' cannot also be "
                            + "a parent to the object model '"
                            + this.objectId.asString() + "'");
                }
            }

            //validate referenced models
            for (OperationBuilderImpl opb : operations.values()) {
                //operations can validate themselves
                opb.validate();
            }
            //for parents and specializations, all we need to do is make sure 
            //they are registered in the object cache, which ensures they 
            //will be validated
            validateRegistered(parents);
            validateRegistered(specializations);
        }

        private Set<DomainId> getRelatedDomains() {
            return relatedDomains;
        }

        private void trackRelated(ObjectId relatedObjectId) {
            final DomainId dId = relatedObjectId.getDomainId();
            if (!dId.equals(this.objectId.getDomainId())) {
                relatedDomains.add(dId);
            }
        }

        private ImmutableObjectModel buildImmutable() throws ModelException {
            //build the (immutable) object model by iterating through the 
            //object model components, deferring build of related models to 
            //prevent modeling deadlocks as needed
            final Set<ObjectModel> parentModels
                    = modelDeferringDeadlocks(parents);
            final Set<ObjectModel> specializedModels
                    = modelDeferringDeadlocks(specializations);
            final Set<OperationModel> operationModels
                    = modelDeferringDeadlocks(operations);
            return new ImmutableObjectModel(objectId,
                    this.attributes,
                    operationModels,
                    parentModels,
                    specializedModels);
        }

    }

    private class OperationBuilderImpl implements OperationModelBuilder {

        private final ObjectBuilderImpl containerBuilder;
        private final String operationName;
        private String description; //can be null
        private final Map<String, ObjectId> parameters = new ConcurrentHashMap<>();
        private ObjectId returnType; //can be null, indicating Void return 
        private final Set<ObjectId> exceptions
                = Collections.synchronizedSet(new HashSet<>());
        private ModelException buildException; //null if builder had no problem during construction

        public OperationBuilderImpl(ObjectBuilderImpl containerBuilder,
                String operationName) {
            this.containerBuilder = containerBuilder;
            this.operationName = operationName;
        }

        @Override
        public OperationModelBuilder withDescription(String desc) {
            this.description = desc;
            return this;
        }

        @Override
        public OperationModelBuilder withParameter(String paramName,
                ObjectId paramModelId) throws DuplicateParametersException {

            //tell container about possible external object reference
            containerBuilder.trackRelated(paramModelId);

            if (parameters.containsKey(paramName)) {
                if (!parameters.get(paramName).equals(paramModelId)) {
                    buildException = new DuplicateParametersException(
                            containerBuilder.objectId, paramName,
                            parameters.get(paramName), paramModelId);
                    throw (DuplicateParametersException) buildException;
                }
            } else {
                //parameter hasn't previously been registered, ensure it is 
                //registered as an ObjectModel
                registerObjectType(paramModelId);
                parameters.put(paramName, paramModelId);
            }
            return this;
        }

        @Override
        public OperationModelBuilder withReturn(ObjectId returnModel) {
            registerObjectType(returnModel);

            //tell container about possible external object reference
            containerBuilder.trackRelated(returnModel);

            returnType = returnModel;
            return this;
        }

        @Override
        public OperationModelBuilder voidReturn() {
            returnType = null;
            return this;
        }

        @Override
        public OperationModelBuilder withException(ObjectId exceptionModel) {
            registerObjectType(exceptionModel);

            //tell container about possible external object reference
            containerBuilder.trackRelated(exceptionModel);

            exceptions.add(exceptionModel);
            return this;
        }

        void validate() throws ModelException {
            if (buildException != null) {
                throw buildException;
            }

            if (operationName == null || operationName.isEmpty()) {
                throw new InvalidOperationNameException(operationName);
            }

            //validate referenced object are present in the object cache,
            //which will ensure that they are checked
            validateRegistered(parameters.values());
            validateRegistered(exceptions);
            if (returnType != null) {
                validateRegistered(Arrays.asList(returnType));
            }
        }

    }

    private static class DeferredImmutableObjectModel implements ObjectModel {

        private final ObjectId objectId;
        private ObjectBuilderImpl builder;
        private ImmutableObjectModel deferredModel;

        public DeferredImmutableObjectModel(ObjectId objectId,
                ObjectBuilderImpl builder) {
            this.objectId = objectId;
            this.builder = builder;
        }

        @Override
        public String getName() {
            return this.objectId.getObjectName();
        }

        @Override
        public String getDomainName() {
            return this.objectId.getDomainName();
        }

        @Override
        public MetaVersion getDomainVersion() {
            return this.objectId.getDomainVersion();
        }

        @Override
        public Map<String, String> getAttributes() {
            try {
                return execute().getAttributes();
            } catch (ModelException ex) {
                logDeferredError("attributes", ex);
            }
            return Collections.EMPTY_MAP;
        }

        @Override
        public Optional<String> getAttribute(String attributeName) {
            return Optional.ofNullable(getAttributes().get(attributeName));
        }

        @Override
        public Collection<OperationModel> getDeclaredOperations() {
            try {
                return execute().getDeclaredOperations();
            } catch (ModelException ex) {
                logDeferredError("declared operations", ex);
            }
            return Collections.EMPTY_LIST;
        }

        @Override
        public Collection<OperationModel> getOperations() {
            try {
                return execute().getOperations();
            } catch (ModelException ex) {
                logDeferredError("all operations", ex);
            }
            return Collections.EMPTY_LIST;
        }

        @Override
        public Collection<ObjectModel> getParents() {
            try {
                return execute().getParents();
            } catch (ModelException ex) {
                logDeferredError("inherited models", ex);
            }
            return Collections.EMPTY_LIST;
        }

        @Override
        public Collection<ObjectModel> getSpecialized() {
            try {
                return execute().getSpecialized();
            } catch (ModelException ex) {
                logDeferredError("specialized models", ex);
            }
            return Collections.EMPTY_LIST;
        }

        private void logDeferredError(String attemptedMethod, Throwable ex) {
            logger.log(Level.SEVERE, "Unable to retreive " + attemptedMethod + " info "
                    + "for '" + objectId.asString() + "'", ex);
        }

        /**
         * Return deferred model, instantiating it as needed.
         *
         * @return
         * @throws ModelException
         */
        synchronized ImmutableObjectModel execute() throws ModelException {
            if (deferredModel != null) {
                try {
                    deferredModel = builder.buildImmutable();
                } catch (Throwable ex) {
                    throw new DeferredModelException(objectId, ex);
                }
                builder = null; //free up reference
            }
            return deferredModel;
        }
    }
}
