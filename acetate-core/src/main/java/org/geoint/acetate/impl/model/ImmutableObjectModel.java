package org.geoint.acetate.impl.model;

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
import org.geoint.acetate.impl.model.ImmutableOperationModel.ImmutableOperationAddress;
import org.geoint.acetate.model.Composable;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.ModelAddress;
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
public abstract class ImmutableObjectModel<T> implements ObjectModel<T> {

    private final DomainModel model;
    private final ImmutableObjectAddress address;
    private final String domainObjectName;
    private final Set<ObjectModel<? super T>> parents;
    private final Optional<String> description;
    private final Collection<ImmutableOperationModel<?>> operations;
    private final Collection<ImmutableCompositeModel<?>> composites;
    private final Collection<ImmutableAggregateModel<?>> aggregates;
    private final Collection<ComponentConstraint> constraints;
    private final Collection<ComponentAttribute> attributes;
    private final BinaryCodec<T> binaryCodec;
    private final CharacterCodec<T> charCodec;

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
     * @param address domain model component contextual address for the object
     * @param name required domain-unique object display name
     * @param description optional object description (may be null)
     * @param parentObjectNames explictly defined object inheritance
     * @param components all object components
     * @param constraints constraints placed on this object
     * @param attributes attributes defined for this object
     * @param binaryCodec codec used to convert object to/from binary
     * @param charCodec codec used to convert object to/from UTF-8 characters
     * @throws IncompleteModelException
     * @throws ComponentCollisionException
     */
    protected ImmutableObjectModel(DomainModel model,
            ImmutableObjectAddress address,
            String name,
            String description,
            Collection<String> parentObjectNames,
            Collection<Composable> components,
            Collection<ComponentConstraint> constraints,
            Collection<ComponentAttribute> attributes,
            BinaryCodec<T> binaryCodec,
            CharacterCodec<T> charCodec)
            throws IncompleteModelException, ComponentCollisionException {
        this.model = model;
        this.address = address;
        this.domainObjectName = name;
        this.description = Optional.ofNullable(description);

        Set<ObjectModel<? super T>> parentObjects = new HashSet<>();
        Collection<ImmutableOperationModel<?>> ops = new HashSet<>();
        Collection<ImmutableCompositeModel<?>> comps = new HashSet<>();
        Collection<ImmutableAggregateModel<?>> aggs = new HashSet<>();

        //add explictly defined parents
        parentObjectNames.stream()
                .map((pn) -> model.getComponents().findByObjectName(pn))
                .filter((o) -> o.isPresent())
                .map((p) -> (ObjectModel<? super T>) p.get())
                .forEach((p) -> {
                    logger.log(Level.FINE, () -> "Domain Object '"
                            + address.asString()
                            + "' inherits from '"
                            + p.getObjectName());
                    parentObjects.add(p);
                });

        for (Composable c : components) {

            if (ComponentFilters.isInherited(c)) {
                //add implict inheritence

                if (parentObjects.add(c.getDeclaringComponent())) {
                    logger.log(Level.FINE, () -> "Object '"
                            + address.asString() + "' inherits from '"
                            + c.getDeclaringComponent().getAddress().asString());
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
        this.operations = Collections.unmodifiableCollection(ops);
        this.composites = Collections.unmodifiableCollection(comps);
        this.aggregates = Collections.unmodifiableCollection(aggs);
        this.constraints = Collections.unmodifiableCollection(new ArrayList<>(constraints));
        this.attributes = Collections.unmodifiableCollection(new ArrayList<>(attributes));
        this.binaryCodec = binaryCodec;
        this.charCodec = charCodec;
    }

    @Override
    public ModelAddress getAddress() {
        return address;
    }

    @Override
    public String getObjectName() {
        return domainObjectName;
    }

    @Override
    public Optional<String> getDescription() {
        return description;
    }

    @Override
    public Collection<ImmutableOperationModel<?>> getOperations() {
        return operations;
    }

    @Override
    public Collection<ImmutableAggregateModel<?>> getAggregates() {
        return aggregates;
    }

    @Override
    public Collection<ImmutableCompositeModel<?>> getComposites() {
        return composites;
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
    public CharacterCodec<T> getCharacterCodec() {
        return charCodec;
    }

    @Override
    public BinaryCodec<T> getBinaryCodec() {
        return binaryCodec;
    }

    @Override
    public DomainModel getDomainModel() {
        return model;
    }

    @Override
    public String toString() {
        return address.asString();
    }

    public abstract class ImmutableObjectAddress implements ModelAddress {

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
