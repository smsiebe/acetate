package org.geoint.acetate.impl.model;

import java.util.Objects;
import org.geoint.acetate.model.address.ComponentAddress;
import org.geoint.acetate.model.address.CompositeAddress;
import org.geoint.acetate.model.address.ObjectAddress;
import org.geoint.acetate.model.address.OperationAddress;

/**
 * Domain model object component address.
 */
public abstract class ImmutableComponentAddress
        implements ComponentAddress {

    private static final String DOMAIN_VERSION_SEPARATOR = "-";

    /**
     * Creates the context path for a base domain model object.
     *
     * @param domainName domain name
     * @param domainVersion domain version
     * @param componentName name of the component
     * @return component context path for a base component definition
     */
    public ImmutableObjectAddress object(String domainName,
            long domainVersion, String componentName) {
        return new ImmutableBaseObjectAddress(domainName, domainVersion,
                componentName);
    }

    @Override
    public String asString() {

    }

    public abstract class ImmutableObjectAddress extends ImmutableComponentAddress
            implements ObjectAddress {

        public ImmutableCompositeAddress composite(String localName) {
            return new ImmutableCompositeAddress(this, localName);
        }

        public ImmutableAggregateAddress aggregate(String localName) {
            return new ImmutableAggregateAddress(this, localName);
        }

        public ImmutableOperationAddress operation(String localName) {
            return new ImmutableOperationAddress(this, localName);
        }

    }

    private static class ImmutableBaseObjectAddress
            extends ImmutableObjectAddress {

        private final String domainName;
        private final long domainVersion;
        private final String objectName;

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

        @Override
        public String getObjectName() {
            return objectName;
        }

    }

    public static class ImmutableCompositeAddress
            extends ImmutableObjectAddress implements CompositeAddress {

        private final ImmutableObjectAddress containerAddress;
        private final String localName;

        private ImmutableCompositeAddress(
                ImmutableObjectAddress containerAddress,
                String localName) {
            this.containerAddress = containerAddress;
            this.localName = localName;
        }

        @Override
        public String getLocalName() {
            return localName;
        }

        @Override
        public String getObjectName() {
            return localName;
        }

        @Override
        public String getDomainName() {
            return containerAddress.getDomainName();
        }

        @Override
        public long getDomainVersion() {
            return containerAddress.getDomainVersion();
        }

    }

    public static class ImmutableOperationAddress
            extends ImmutableCompositeAddress implements OperationAddress {

        private ImmutableOperationAddress(
                ImmutableBaseObjectAddress containerAddress,
                String localName) {
            super(containerAddress, localName);
        }

        public ImmutableCompositeAddress parameter(String paramName) {
            return new ImmutableCompositeAddress(this, paramName);
        }

        public ImmutableEventAddress returns() {

        }

    }

    public static class ImmutableEventAddress extends ImmutableObjectAddress {

        private final ImmutableOperationAddress operationAddress;
        private final String eventName;

        public ImmutableEventAddress(ImmutableOperationAddress operationAddress,
                String eventName) {
            this.operationAddress = operationAddress;
            this.eventName = eventName;
        }

        @Override
        public String getDomainName() {
            return operationAddress.getDomainName();
        }

        @Override
        public long getDomainVersion() {
            return operationAddress.getDomainVersion();
        }

        @Override
        public String getObjectName() {
            return eventName;
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
