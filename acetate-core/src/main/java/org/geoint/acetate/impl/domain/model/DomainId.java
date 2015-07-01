package org.geoint.acetate.impl.domain.model;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.geoint.acetate.domain.model.DomainModel;
import org.geoint.acetate.meta.MetaVersion;

/**
 * Unique identity of a domain model/ontology.
 */
public class DomainId {

    private final String domainId;
    private final String name;
    private final MetaVersion version;

    static final char VERSION_SEPARATOR = ':';
    static final Pattern DOMAINID_PATTERN
            = Pattern.compile("(.*)"
                    + VERSION_SEPARATOR
                    + "(" + (MetaVersionImpl.VERSION_PATTERN.pattern()) + ")"
            );

    private static final Map<String, WeakReference<DomainId>> cache
            = new WeakHashMap<>();
    /**
     * Default domain model used if not defined.
     */
    public static final DomainId DEFAULT_DOMAIN
            = DomainId.getInstance(DomainModel.DEFAULT_DOMAIN_NAME,
                    MetaVersionImpl.DEFAULT_VERSION);

    /**
     *
     * @param domainName
     * @param domainVersion
     * @return
     * @throws NullPointerException if domain name was null or empty or domain
     * version was null
     */
    public static DomainId getInstance(String domainName,
            MetaVersion domainVersion) {

        if (domainName == null || domainName.isEmpty()) {
            throw new NullPointerException("Domain name must not be null.");
        }

        if (domainVersion == null) {
            throw new NullPointerException("Domain version must not be "
                    + "null.");
        }

        final String domainId = generateId(domainName, domainVersion);

        synchronized (cache) {
            if (!cache.containsKey(domainId)) {
                cache.put(domainId, new WeakReference(
                        new DomainId(domainId,
                                domainName,
                                domainVersion)
                ));
            }
        }
        return cache.get(domainId).get();
    }

    public static DomainId valueOf(String domainId)
            throws InvalidDomainIdentifierException {
        if (cache.containsKey(domainId)) {
            return cache.get(domainId).get();
        }

        Matcher m = DOMAINID_PATTERN.matcher(domainId);
        if (!m.matches()) {
            throw new InvalidDomainIdentifierException("domainId", "Invalid "
                    + "domainId format.");
        }

        for (int i = 0; i < m.groupCount(); i++) {
            System.out.println("Group " + i + ": " + m.group(i));
        }
        final DomainId id = new DomainId(domainId,
                m.group(1),
                MetaVersionImpl.valueOf(m.group(2)));
        cache.put(domainId, new WeakReference(id));

        return id;
    }

    private DomainId(String domainId, String domainName,
            MetaVersion domainVersion) {
        this.domainId = domainId;
        if (domainName == null || domainName.isEmpty()) {
            throw new NullPointerException("Domain name must not be null or an "
                    + "empty string.");
        }
        this.name = domainName; //TODO intern?
        this.version = domainVersion;
    }

    public String getName() {
        return name;
    }

    public MetaVersion getVersion() {
        return version;
    }

    /**
     * Returns a contractually defined string format uniquely identifying a
     * domain model by domainName/domainVersion.
     *
     * This method provides a contract that it will always format the ObjectId
     * in the following format: <i>domainName:domainVersion</i>.
     *
     * If the domain domainName contains a colon it is not escaped.
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
     * @return domainAddress instance represented as a String
     */
    @Override
    public String toString() {
        return asString();
    }

    private static String generateId(String domainName, MetaVersion version) {
        StringBuilder sb = new StringBuilder();
        sb.append(domainName)
                .append(VERSION_SEPARATOR)
                .append(version.asString());
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.name);
        hash = 73 * hash + Objects.hashCode(this.version);
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
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return Objects.equals(this.version, other.version);
    }

}
