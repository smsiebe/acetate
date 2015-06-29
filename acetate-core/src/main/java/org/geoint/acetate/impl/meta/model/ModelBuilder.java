package org.geoint.acetate.impl.meta.model;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import org.geoint.acetate.meta.MetaVersion;
import org.geoint.acetate.meta.model.ModelException;
import org.geoint.acetate.meta.model.ObjectModel;

/**
 * Fluid API to programmatically create one or more ObjectModel instances.
 *
 * The builder API is thread safe.
 */
public final class ModelBuilder {

    private final Map<DomainId, WeakReference<ObjectBuilderImpl>> objects
            = new WeakHashMap<>();

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
                DomainId.getInstance(domainName, domainVersion, objectName)
        );
    }

    public ObjectModelBuilder forObject(DomainId domainId) {
        return registerObjectType(domainId);
    }

    /**
     * Creates and caches a new ObjectModel builder if one does not already
     * exist for this class.
     *
     * @param objectName
     */
    private ObjectBuilderImpl registerObjectType(DomainId domainId) {
        return registerObjectType(domainId);
    }

    private ObjectBuilderImpl registerObjectType(String domainAddress)
            throws InvalidDomainIdentifierException {
        final DomainId domainId = DomainId.valueOf(domainAddress);
        synchronized (objects) {
            //register parent class model builder if it hasn't been already
            if (!objects.containsKey(domainId)) {
                ObjectBuilderImpl builder = new ObjectBuilderImpl(domainId);
                objects.put(domainId, new WeakReference(builder));
                return builder;
            }
        }
        return objects.get(domainAddress).get();
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
    public Collection<ObjectModel> buildObjectModels()
            throws ModelException {

        final Map<String, ObjectModel> models = new HashMap<>();
        for (WeakReference<ObjectBuilderImpl> oref : objects.values()) {
            final ObjectBuilderImpl ob = oref.get();
            //ask each object model builder to validate itself.  since each 
            //object references (ie operation param/return/exception) is 
            //registered, they will throw an exception if they are not fully 
            //modeled 
            ob.validate();

            //build the (immutable) object models by iterating through the 
            //object model builders, deferring build of related models to 
            //prevent deadlocks
            final Set<ObjectModel> parents = new HashSet<>();

        }
        return models.values();
    }

    private class ObjectBuilderImpl implements ObjectModelBuilder {

        private final DomainId domainId;
        private final Map<String, String> attributes
                = new ConcurrentHashMap<>();
        private final Map<String, OperationBuilderImpl> operations
                = new HashMap<>();
        private final Set<DomainId> parents
                = Collections.synchronizedSet(new HashSet<>());
        private final Set<DomainId> specializations
                = Collections.synchronizedSet(new HashSet<>());

        public ObjectBuilderImpl(String domainName,
                MetaVersion domainVersion,
                String objectName) {
            this(DomainId.getInstance(domainName, domainVersion, objectName));
        }

        public ObjectBuilderImpl(DomainId domainId) {
            this.domainId = domainId;
        }

        @Override
        public ObjectModelBuilder specializes(String parentObjectName) {
            final DomainId parentId = DomainId.getInstance(
                    domainId.getDomainName(),
                    domainId.getDomainVersion(),
                    parentObjectName);

            final ObjectBuilderImpl parentClassBuilder
                    = registerObjectType(parentId);
            //tell the parent model builder that this object specializes it
            parentClassBuilder.specializations.add(this.domainId);

            //tell this model about the parent object model
            this.parents.add(parentId);

            return this;
        }

        @Override
        public ObjectModelBuilder withAttribute(String name, String value) {
            this.attributes.put(name, value);
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
                        = new OperationBuilderImpl(operationName);
                operations.put(operationName, opBuilder);
                return opBuilder;
            }
        }

        private void validate() throws ModelException {
            //validate itself 
            for (DomainId s : specializations) {
                //verfiy there aren't any circular dependencies 
                if (this.parents.contains(s)) {
                    throw new ModelInheritanceException(this.domainId,
                            "Specialized class '" + s.asString() + "' cannot also be "
                            + "a parent to the object model '"
                            + this.domainId.asString() + "'");
                }
            }

            //validate referenced models
            for (DomainId p : parents) {
                WeakReference<ObjectBuilderImpl> pref = objects.get(p);
                if (pref == null) {
                    throw new ModelInheritanceException(this.domainId, "Unable "
                            + "to find parent model '" + p.asString() + "'");
                }
                pref.get().validate();
            }
            
            
        }

        private void validate()
    }

    private class OperationBuilderImpl implements OperationModelBuilder {

        private final DomainId containerId;
        private final String operationName;
        private String description; //can be null
        private final Map<String, DomainId> parameters = new ConcurrentHashMap<>();
        private DomainId returnType; //can be null, indicating Void return 
        private final Set<DomainId> exceptions
                = Collections.synchronizedSet(new HashSet<>());
        private ModelException buildException; //null if builder had no problem during construction

        public OperationBuilderImpl(DomainId containerId, String operationName) {
            this.containerId = containerId;
            this.operationName = operationName;
        }

        @Override
        public OperationModelBuilder withDescription(String desc) {
            this.description = desc;
            return this;
        }

        @Override
        public OperationModelBuilder withParameter(String paramName,
                DomainId paramModelId) throws DuplicateParametersException {
            if (parameters.containsKey(paramName)) {
                if (!parameters.get(paramName).equals(paramModelId)) {
                    buildException = new DuplicateParametersException(
                            containerId, paramName, parameters.get(paramName), paramModelId);
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
        public OperationModelBuilder withReturn(DomainId returnModel) {
            registerObjectType(returnModel);
            returnType = returnModel;
            return this;
        }

        @Override
        public OperationModelBuilder voidReturn() {
            returnType = null;
            return this;
        }

        @Override
        public OperationModelBuilder withException(DomainId exceptionModel) {
            registerObjectType(exceptionModel);
            exceptions.add(exceptionModel);
            return this;
        }

        private void validate() throws ModelException {
            if (buildException != null) {
                throw buildException;
            }

            if (operationName == null || operationName.isEmpty()) {
                throw new InvalidOperationNameException(operationName);
            }
        }

    }
}
