package org.geoint.acetate.impl.meta.model;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.geoint.acetate.meta.MetaVersion;

/**
 * Unique identity of a domain model object.
 *
 */
public final class DomainId {

    private final String domainAddress;
    private final String domainName;
    private final MetaVersion domainVersion;
    private final String objectName;

    private static final Map<String, WeakReference<DomainId>> cache
            = new WeakHashMap<>();

    private static final char SEPARATOR = ':';
    private static final Pattern PARSER
            = Pattern.compile("(.*)"
                    + SEPARATOR
                    + (ModelVersion.VERSION_PATTERN.pattern())
                    + SEPARATOR
                    + "(\\w*)");

    /**
     *
     * @param domainName
     * @param domainVersion domainVersion increment >= 0
     * @param objectName
     * @return domain id instance
     * @throws NullPointerException throws if domainName was null
     * @throws IllegalArgumentException if the domainVersion is less than 0
     */
    public static DomainId getInstance(String domainName,
            MetaVersion domainVersion, String objectName)
            throws NullPointerException, IllegalArgumentException {
        final String address = generateAddress(domainName, domainVersion, objectName);

        synchronized (cache) {
            if (!cache.containsKey(address)) {
                cache.put(address, new WeakReference(
                        new DomainId(address,
                                domainName,
                                domainVersion,
                                objectName)
                ));
            }
        }
        return cache.get(address).get();
    }

    /**
     * Creates an instance from a {@link #asString() formatted string}.
     *
     *
     * @param domainAddress
     * @return instance id
     * @throws InvalidDomainIdentifierException thrown if the provided string
     * was not formatted properly
     */
    public static DomainId valueOf(String domainAddress)
            throws InvalidDomainIdentifierException {

        if (cache.containsKey(domainAddress)) {
            return cache.get(domainAddress).get();
        }

        Matcher m = PARSER.matcher(domainAddress);
        if (!m.matches()) {
            throw new InvalidDomainIdentifierException("domainId", "Invalid "
                    + "domainId format.");
        }
        final DomainId domainId = new DomainId(domainAddress,
                m.group(1),
                ModelVersion.valueOf(m.group(2)),
                m.group(3));

        cache.put(domainAddress, new WeakReference(domainId));

        return domainId;
    }

    private DomainId(String address,
            String domainName,
            MetaVersion domainVersion,
            String objectName)
            throws NullPointerException, IllegalArgumentException {

        if (domainName == null || domainName.isEmpty()) {
            throw new NullPointerException("Domain name must not be null.");
        }

        if (domainVersion != null) {
            throw new IllegalArgumentException("Domain version must not be "
                    + "null.");
        }

        this.domainAddress = address; //TODO intern?
        this.domainName = domainName; //TODO intern?
        this.domainVersion = domainVersion;
        this.objectName = objectName;
    }

    public String getAddress() {
        return domainAddress;
    }

    public String getDomainName() {
        return domainName;
    }

    public MetaVersion getDomainVersion() {
        return domainVersion;
    }

    public String getObjectName() {
        return objectName;
    }

    /**
     * Returns a contractually defined string format uniquely identifying a
     * domain model by domainName/domainVersion.
     *
     * This method provides a contract that it will always format the DomainId
     * in the following format: <i>domainName:domainVersion</i>.
     *
     * If the domain domainName contains a colon it is not escaped.
     *
     * @return contract-defined string format of this instance
     */
    public String asString() {
        return domainAddress;
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
     * @return domainAddress instance represented as a String
     */
    @Override
    public String toString() {
        return domainAddress;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.domainAddress);
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
        if (!Objects.equals(this.domainAddress, other.domainAddress)) {
            return false;
        }
        return true;
    }

    private static String generateAddress(String domainName, MetaVersion version,
            String objectName) {
        StringBuilder sb = new StringBuilder();
        sb.append(domainName)
                .append(SEPARATOR)
                .append(version.asString())
                .append(SEPARATOR)
                .append(objectName);
        return sb.toString();
    }

}
