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

        private final List<ImmutableCompositeAddress> components;
        public abstract ImmutableCompositeAddress composite(String localName);

        public abstract ImmutableCompositeAddress aggregate(String localName);

        public abstract OperationAddress operation(String localName);

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

    }

    public static class ImmutableOperationAddress
            extends ImmutableCompositeAddress implements OperationAddress {

        public ObjectAddress parameter(String paramName) {

        }

        public ObjectAddress returned() {

        }

    }

    private static class ImmutableCompositeAddress
            extends ImmutableComponentAddress {

        private final ImmutableComponentAddress parent;
    }

    private static class ImmutableAggregateAddress
            extends ImmutableCompositeAddress {

    }

    private static class ImmutableOperationAddress
            extends ImmutableCompositeAddress {

    }

    @Override
    public String asString() {
        return address;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.domainName);
        hash = 41 * hash + (int) (this.domainVersion ^ (this.domainVersion >>> 32));
        hash = 41 * hash + Objects.hashCode(this.address);
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
        final ImmutableComponentAddress other = (ImmutableComponentAddress) obj;
        if (!Objects.equals(this.domainName, other.domainName)) {
            return false;
        }
        if (this.domainVersion != other.domainVersion) {
            return false;
        }
        return Objects.equals(this.address, other.address);

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

    public static class ImmutableObjectAddress extends ImmutableObjectAddress {

        private ImmutableObjectAddress(ImmutableComponentAddress baseContext,
                ComponentType delimiter) {
            super(baseContext, delimiter);
        }

        private ImmutableObjectAddress(String domainName, long domainVersion,
                String objectName) {
            super(domainName, domainVersion, objectName);
        }

        private ImmutableObjectAddress(ImmutableComponentAddress baseContext, String contextName) {
            super(baseContext, ComponentType.OBJECT_COMPOSITE, contextName);
        }

        private ImmutableObjectAddress(ImmutableComponentAddress baseContext,
                ComponentType delimiter, String contextName) {
            super(baseContext, delimiter, contextName);
        }

        /**
         * Creates a new context path for a composite object.
         *
         * @param compositeLocalName local composite name
         * @return component context path for a component in a composite context
         */
        public ImmutableComponentAddress composite(String compositeLocalName) {
            return new ImmutableComponentAddress(this, compositeLocalName);
        }

        /**
         * Creates a new context path for an aggregate object.
         *
         * @param aggregateLocalName local composite name
         * @return component context path for a component in a composite context
         */
        public ImmutableComponentAddress aggregate(String aggregateLocalName) {
            return new ImmutableComponentAddress(this, aggregateLocalName);
        }

        /**
         * Creates a new context path for an operation of this component.
         *
         * @param operationName
         * @return context path of the component operation
         */
        public ImmutableOperationPath operation(String operationName) {
            return new ImmutableOperationPath(this, operationName);
        }
    }

    public static class ImmutableOperationPath extends ImmutableComponentAddress {

        private ImmutableOperationPath(ImmutableComponentAddress baseContext,
                String contextName) {
            super(baseContext, ComponentType.OPERATION, contextName);
        }

        public ImmutableComponentAddress parameter(String paramName) {
            return new ImmutableComponentAddress(this,
                    ComponentType.OPERATION_PARAM, paramName);
        }

        public ImmutableComponentAddress returned() {
            return new ImmutableComponentAddress(this, ComponentType.OPERATION_RETURN);
        }

    }

}
