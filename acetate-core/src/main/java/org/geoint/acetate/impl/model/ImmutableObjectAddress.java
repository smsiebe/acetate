package org.geoint.acetate.impl.model;

import java.util.Objects;
import org.geoint.acetate.model.ModelAddress;

/**
 * Domain model object component address.
 */
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
        final ImmutableObjectAddress other = (ImmutableBaseObjectAddress) obj;
        if (!Objects.equals(this.asString(), other.asString())) {
            return false;
        }
        return true;
    }

    public static class ImmutableCompositeAddress extends ImmutableObjectAddress {

        private final ImmutableObjectAddress containerAddress;
        private final String localName;

        public ImmutableCompositeAddress(
                ImmutableObjectAddress containerAddress,
                String localName) {
            this.containerAddress = containerAddress;
            this.localName = localName;
        }

        @Override
        public String getDomainName() {
            return containerAddress.getDomainName();
        }

        @Override
        public long getDomainVersion() {
            return containerAddress.getDomainVersion();
        }

        @Override
        public String asString() {

        }

    }

    public static class ImmutableOperationAddress
            extends ImmutableCompositeAddress {

        private ImmutableOperationAddress(
                ImmutableBaseObjectAddress containerAddress,
                String operationName) {
            super(containerAddress, operationName);
        }

        public ImmutableCompositeAddress parameter(String paramName) {
            return new ImmutableCompositeAddress(this, paramName);
        }

        public ImmutableEventAddress returns() {

        }

    }

    private static enum AddressScheme {

        MODEL_SCHEME("model://"),
        VIEW_SCHEME("view://");

        private final String scheme;

        private AddressScheme(String scheme) {
            this.scheme = scheme;
        }

        public String getScheme() {
            return scheme;
        }

    }

    private static enum ComponentType {

        OBJECT_COMPOSITE("/"),
        MAP_COMPOSITE("#"),
        OPERATION("!"),
        OPERATION_PARAM("?"),
        OPERATION_RETURN(">");

        private final String separator;

        private ComponentType(String separator) {
            this.separator = separator;
        }

        public String getSeparator() {
            return separator;
        }

    }

}
