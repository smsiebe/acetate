package org.geoint.acetate.model;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Unique identity of a domain model.
 *
 */
public final class DomainId {

    private String domainId;
    private String name;
    private long version;

    private static final char SEPARATOR = ':';
    private static final Pattern PARSER = Pattern.compile(".*:\\d");

    public DomainId(String domainName, long domainVersion) {
        this(generateId(domainName, domainVersion), domainName, domainVersion);
    }

    private DomainId(String domainId, String domainName, long domainVersion) {
        this.domainId = domainId;
        this.name = domainName;
        this.version = domainVersion;
    }

    /**
     * Creates an instance 
     * @param domainId
     * @return 
     */
    public static DomainId valueOf(String domainId) {

    }

    public String getDomainId() {
        return domainId;
    }

    public String getName() {
        return name;
    }

    public long getVersion() {
        return version;
    }

    /**
     * Returns a contractually defined string format uniquely identifying a 
     * domain model by name/version.
     *
     * This method provides a contract that it will always format the DomainId
     * in the following format: <i>name:version</i>.
     *
     * If the domain name contains a colon it is not escaped.
     *
     * @return contract-defined string format of this instance
     */
    public String asString() {
        return domainId;
    }

    /**
     * Returns the DomainId as a String, but does not define a contractual
     * format.
     *
     * This method may, but does not guarantee, that returned string will always
     * be formatted the same way. If the calling code requires the domain id to
     * always be formatted the same way (defined by contact), use 
     * {@link #asString() }.
     *
     * @return domainId instance represented as a String
     */
    @Override
    public String toString() {
        return domainId;
    }

    private static String generateId(String name, long version) {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(SEPARATOR).append(version);
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.domainId);
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
        final DomainId other = (DomainId) obj;
        if (!Objects.equals(this.domainId, other.domainId)) {
            return false;
        }
        return true;
    }

}
