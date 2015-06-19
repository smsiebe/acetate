package org.geoint.acetate.impl.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import org.geoint.acetate.impl.model.ImmutableCompositeObjectModel.ImmutableCompositeAddress;
import org.geoint.acetate.impl.model.ImmutableOperationModel.ImmutableOperationAddress;
import org.geoint.acetate.model.ComponentAddress;
import org.geoint.acetate.model.ContextualModelComponent;
import org.geoint.acetate.model.ObjectModel;
import org.geoint.acetate.model.attribute.ModelAttribute;
import org.geoint.acetate.model.attribute.Inherited;
import org.geoint.acetate.model.builder.ComponentCollisionException;
import org.geoint.acetate.model.builder.IncompleteModelException;
import org.geoint.acetate.model.constraint.DataConstraint;

/**
 * Domain model object.
 *
 * @param <T> java object representation of this model
 */
public class ImmutableObjectModel<T> implements ObjectModel<T> {

    private final ImmutableObjectAddress address;
    private final String objectName;
    private final Set<ComponentAddress> parents;
    private final Optional<String> description;
    private final Map<String, ImmutableCompositeObjectModel<?>> composites;
    private final Collection<DataConstraint> constraints;
    private final Collection<ModelAttribute> attributes;

    private final static Logger logger
            = Logger.getLogger("org.geoint.acetate.model");

    /**
     * Create a domain object with the absolute state of the domain object.
     *
     * This constructor creates shallow defensive copies of each collection
     * provided.
     * <p>
     * This constructor <b>does not</b> perform any composite/inheritance
     * roll-up. Use builders for this.
     *
     * @param domainName
     * @param domainVersion
     * @param objectName required domain-unique object display name
     * @param description optional object description (may be null)
     * @param parents addresses to hierarchical parent object models from which
     * this model extends
     * @param composites all object components
     * @param constraints constraints placed on this object
     * @param attributes attributes defined for this object
     * @throws IncompleteModelException
     * @throws ComponentCollisionException
     */
    public ImmutableObjectModel(
            String domainName,
            long domainVersion,
            String objectName,
            String description,
            Set<ComponentAddress> parents,
            Collection<ImmutableCompositeObjectModel> composites,
            Collection<DataConstraint> constraints,
            Collection<ModelAttribute> attributes)
            throws IncompleteModelException, ComponentCollisionException {
        this.address = new ImmutableBaseObjectAddress(domainName,
                domainVersion,
                objectName);
        this.objectName = objectName;
        this.description = Optional.ofNullable(description);
        this.parents = Collections.unmodifiableSet(parents);

        //validate composites
        Map<String, ImmutableCompositeObjectModel<?>> comps = new HashMap<>();

        for (ImmutableCompositeObjectModel<?> c : composites) {
            //ensure that inherited composites added to the model are from a 
            //defined parent model
            Collection<Inherited> inheritedFrom = c.getAttributes(Inherited.class);
            if (!inheritedFrom.isEmpty()) {
                for (Inherited ci : inheritedFrom) {
                    if (!this.parents.contains(ci.getDefinedAddress())) {
                        throw new IncompleteModelException(domainName,
                                domainVersion, "Composite component '"
                                + c.getAddress()
                                + "' is inherited from '"
                                + ci.getDefinedAddress()
                                + "' but containing object model '"
                                + this.address.toString()
                                + "' does not inherit from this object.");
                    }
                }
            }

            //ensure that there isn't a local composite name collision within
            //the object
            if (comps.containsKey(c.getName())) {
                throw new ComponentCollisionException(c.getAddress(),
                        "Unable to add composite '"
                        + c.getAddress()
                        + "' to object model due to component name collision "
                        + "with '"
                        + comps.get(c.getName()).getAddress()
                        + "'.");
            }

            //add composite
            comps.put(c.getName(), c);
        }

        this.composites = Collections.unmodifiableMap(comps);
        this.constraints = Collections.unmodifiableCollection(new ArrayList<>(constraints));
        this.attributes = Collections.unmodifiableCollection(new ArrayList<>(attributes));
    }

    @Override
    public Set<ComponentAddress> getParents() {
        return parents;
    }

    @Override
    public ComponentAddress getAddress() {
        return address;
    }

    @Override
    public String getName() {
        return objectName;
    }

    @Override
    public Optional<String> getDescription() {
        return description;
    }

    @Override
    public Collection<ModelAttribute> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<DataConstraint> getConstraints() {
        return constraints;
    }

    @Override
    public String toString() {
        return address.asString();
    }

    @Override
    public Collection<? extends ContextualModelComponent<?>> getComposites() {
        return composites.values();
    }

    public static abstract class ImmutableObjectAddress implements ComponentAddress {

        protected static final char DOMAIN_VERSION_SEPARATOR = '-';
        protected static final char COMPONENT_SEPARATOR = '/';

        /**
         * Create a component address for a composite object contained by this
         * object.
         *
         * @param localName
         * @return composite component address
         */
        public ImmutableObjectAddress composite(String localName) {
            return new ImmutableCompositeAddress(this, localName);
        }

        /**
         * Create a component address for an aggregate object contained by this
         * object.
         *
         * @param localName
         * @return aggregate component address
         */
        public ImmutableObjectAddress aggregate(String localName) {
            return new ImmutableCompositeAddress(this, localName);
        }

        /**
         * Create a component address for an operation contained by this object.
         *
         * @param localName
         * @return operation component address
         */
        public ImmutableOperationAddress operation(String localName) {
            return new ImmutableOperationAddress(this, localName);
        }

        @Override
        public String toString() {
            return asString();
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 37 * hash + Objects.hashCode(this.asString());
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final ImmutableObjectAddress other = (ImmutableObjectAddress) obj;
            if (!Objects.equals(this.asString(), other.asString())) {
                return false;
            }
            return true;
        }

    }

    /**
     * Address for a root domain model object.
     */
    public final class ImmutableBaseObjectAddress extends ImmutableObjectAddress {

        private final String domainName;
        private final long domainVersion;
        private final String objectName;

        private static final String OBJECT_SCHEME = "model://";

        /**
         * Create an address for a base domain model object.
         *
         * @param domainName
         * @param domainVersion
         * @param pathComponents
         */
        private ImmutableBaseObjectAddress(String domainName,
                long domainVersion,
                String objectName) {
            this.domainName = domainName;
            this.domainVersion = domainVersion;
            this.objectName = objectName;
        }

        @Override
        public String getDomainName() {
            return domainName;
        }

        @Override
        public long getDomainVersion() {
            return domainVersion;
        }

        public String getObjectName() {
            return objectName;
        }

        @Override
        public String asString() {
            return OBJECT_SCHEME
                    + domainName
                    + DOMAIN_VERSION_SEPARATOR
                    + domainVersion
                    + COMPONENT_SEPARATOR
                    + objectName;
        }

    }

//
//    public static class ImmutableEventAddress extends ImmutableObjectAddress {
//
//        private final ImmutableOperationAddress operationAddress;
//        private final String eventName;
//
//        public ImmutableEventAddress(ImmutableOperationAddress operationAddress,
//                String eventName) {
//            this.operationAddress = operationAddress;
//            this.eventName = eventName;
//        }
//
//        @Override
//        public String getDomainName() {
//            return operationAddress.getDomainName();
//        }
//
//        @Override
//        public long getDomainVersion() {
//            return operationAddress.getDomainVersion();
//        }
//
//        @Override
//        public String getObjectName() {
//            return eventName;
//        }
//
//    }
//
//    private static enum AddressScheme {
//
//        MODEL_SCHEME("model://"),
//        VIEW_SCHEME("view://");
//
//        private final String scheme;
//
//        private AddressScheme(String scheme) {
//            this.scheme = scheme;
//        }
//
//        public String getScheme() {
//            return scheme;
//        }
//
//    }
//
//    private static enum ComponentType {
//
//        OBJECT_COMPOSITE("/"),
//        MAP_COMPOSITE("#"),
//        OPERATION("!"),
//        OPERATION_PARAM("?"),
//        OPERATION_RETURN(">");
//
//        private final String separator;
//
//        private ComponentType(String separator) {
//            this.separator = separator;
//        }
//
//        public String getSeparator() {
//            return separator;
//        }
//
//    }
}
