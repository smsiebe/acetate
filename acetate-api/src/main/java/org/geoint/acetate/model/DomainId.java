package org.geoint.acetate.model;

import java.util.Objects;
import java.util.regex.Matcher;
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
    private static final Pattern PARSER = Pattern.compile("(.*):(\\d)");

    /**
     *
     * @param domainName
     * @param domainVersion version increment >= 0
     * @throws NullPointerException throws if domainName was null
     * @throws IllegalArgumentException if the version is less than 0
     */
    public DomainId(String domainName, long domainVersion)
            throws NullPointerException, IllegalArgumentException {
        this(generateId(domainName, domainVersion), domainName, domainVersion);
    }

    private DomainId(String domainId, String domainName, long domainVersion)
            throws NullPointerException, IllegalArgumentException {

        if (domainName == null || domainName.isEmpty()) {
            throw new NullPointerException("Domain name must not be null.");
        }

        if (domainVersion < 0) {
            throw new IllegalArgumentException("Domain version must be greater "
                    + "than or equal to 0.");
        }

        this.domainId = domainId;
        this.name = domainName;
        this.version = domainVersion;
    }

    /**
     * Creates an instance from a {@link #asString() formatted string}.
     *
     *
     * @param domainId
     * @return instance id
     * @throws InvalidDomainIdentifierException thrown if the provided string
     * was not formatted properly
     */
    public static DomainId valueOf(String domainId)
            throws InvalidDomainIdentifierException {
        Matcher m = PARSER.matcher(domainId);
        if (!m.matches()) {
            throw new InvalidDomainIdentifierException("domainId", "Invalid "
                    + "domainId format.");
        }
        return new DomainId(domainId, m.group(1), Long.valueOf(m.group(2)));
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
