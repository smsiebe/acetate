package org.geoint.acetate.impl.model;

import org.geoint.acetate.impl.model.entity.ImmutableAggregateModel;
import org.geoint.acetate.impl.model.entity.ImmutableOperationModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geoint.acetate.data.transform.BinaryCodec;
import org.geoint.acetate.data.transform.CharacterCodec;
import org.geoint.acetate.impl.model.ImmutableCompositeModel.ImmutableCompositeAddress;
import org.geoint.acetate.impl.model.entity.ImmutableOperationModel.ImmutableOperationAddress;
import org.geoint.acetate.model.ComposableModelComponent;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.ComponentAddress;
import org.geoint.acetate.model.ContextualComponentModel;
import org.geoint.acetate.model.ObjectModel;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.builder.ComponentCollisionException;
import org.geoint.acetate.model.builder.IncompleteModelException;
import org.geoint.acetate.model.constraint.ComponentConstraint;
import org.geoint.acetate.model.util.ComponentFilters;

/**
 * Base domain model object.
 *
 * Implementations of this object must be immutable and thread-safe.
 *
 * @param <T> data type of the object
 */
public class ImmutableObjectModel<T> implements ObjectModel<T> {

    private final DomainModel model;
    private final ImmutableObjectAddress address;
    private final String objectName;
    private final Set<ObjectModel<? super T>> parents;
    private final Optional<String> description;
    private final Collection<ImmutableCompositeModel<?>> composites;
    private final Collection<ComponentConstraint> constraints;
    private final Collection<ComponentAttribute> attributes;

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
     * @param model domain model this object belongs to
     * @param name required domain-unique object display name
     * @param description optional object description (may be null)
     * @param parents object models from which this model inherits
     * @param composites all object components
     * @param constraints constraints placed on this object
     * @param attributes attributes defined for this object
     * @throws IncompleteModelException
     * @throws ComponentCollisionException
     */
    protected ImmutableObjectModel(DomainModel model,
            String name,
            String description,
            Set<ObjectModel<? super T>> parents,
            Collection<? extends ComposableModelComponent<?>> composites,
            Collection<ComponentConstraint> constraints,
            Collection<ComponentAttribute> attributes)
            throws IncompleteModelException, ComponentCollisionException {
        this.model = model;
        this.address = new ImmutableBaseObjectAddress(
                model.getDomainId(), model.getVersion(), name);
        this.objectName = name;
        this.description = Optional.ofNullable(description);

        Set<ObjectModel<? super T>> parentObjects = new HashSet<>();
        Collection<ImmutableCompositeModel<?>> comps = new HashSet<>();

        for (ComposableModelComponent c : composites) {

            if (ComponentFilters.isInherited(c)) {
                
                if (parentObjects.add(c.getContainer())) {
                    logger.log(Level.FINE, () -> "Object '"
                            + address.asString() + "' inherits from '"
                            + c.getContainer().getAddress().asString());
                }
            }

            if (c instanceof ImmutableOperationModel) {
                ops.add((ImmutableOperationModel<?>) c);
            } else if (c instanceof ImmutableAggregateModel) {
                aggs.add((ImmutableAggregateModel<?>) c);
            } else if (c instanceof ImmutableCompositeModel) {
                comps.add((ImmutableCompositeModel<?>) c);
            } else {
                throw new IncompleteModelException(model, "Unknown model component "
                        + c.getClass().getName());
            }
        }

        this.parents = Collections.unmodifiableSet(parentObjects);
        this.composites = Collections.unmodifiableCollection(comps);
        this.constraints = Collections.unmodifiableCollection(new ArrayList<>(constraints));
        this.attributes = Collections.unmodifiableCollection(new ArrayList<>(attributes));
    }

    @Override
    public Set<ObjectModel<? super T>> getParents() {
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
    public Collection<ComponentAttribute> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<ComponentConstraint> getConstraints() {
        return constraints;
    }

    @Override
    public String toString() {
        return address.asString();
    }

    @Override
    public Collection<? extends ComposableModelComponent<?>> getComposites() {
        return composites;
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
}
