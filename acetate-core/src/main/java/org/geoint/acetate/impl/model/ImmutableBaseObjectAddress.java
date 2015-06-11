package org.geoint.acetate.impl.model;

/**
 *
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
