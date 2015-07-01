package org.geoint.acetate.impl.domain.model;

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
public class ObjectId {

    private final DomainId domainId;
    private final String objectAddress;
    private final String objectName;

    private static final Map<String, WeakReference<ObjectId>> cache
            = new WeakHashMap<>();

    private static final char OBJECT_SEPARATOR = '@';

    private static final Pattern OBJECTID_PATTERN
            = Pattern.compile(
                    DomainId.DOMAINID_PATTERN.pattern()
                    + OBJECT_SEPARATOR
                    + "(\\w*)");

    /**
     *
     * @param domainId domain id
     * @param objectName domain-model unique object name
     * @return object id instance
     * @throws NullPointerException throws if domain name, version, or object
     * name was null
     */
    public static ObjectId getInstance(DomainId domainId,
            String objectName)
            throws NullPointerException {

        if (objectName == null || objectName.isEmpty()) {
            throw new NullPointerException("Object name must not be null or "
                    + "empty.");
        }

        final String address = generateAddress(domainId, objectName);

        synchronized (cache) {
            if (!cache.containsKey(address)) {
                cache.put(address, new WeakReference(
                        new ObjectId(address, domainId, objectName)
                ));
            }
        }
        return cache.get(address).get();
    }

    /**
     *
     * @param domainName domain model name
     * @param domainVersion domain model version
     * @param objectName domain-model unique object name
     * @return object id instance
     * @throws NullPointerException throws if domain name, version, or object
     * name was null
     */
    public static ObjectId getInstance(String domainName,
            MetaVersion domainVersion, String objectName)
            throws NullPointerException, IllegalArgumentException {

        return getInstance(
                DomainId.getInstance(domainName, domainVersion),
                objectName);

    }

    /**
     * Creates an instance from a {@link #asString() formatted string}.
     *
     * @param objectId
     * @return instance id
     * @throws InvalidDomainIdentifierException thrown if the provided string
     * was not formatted properly
     */
    public static ObjectId valueOf(String objectId)
            throws InvalidDomainIdentifierException {

        if (cache.containsKey(objectId)) {
            return cache.get(objectId).get();
        }

        Matcher m = OBJECTID_PATTERN.matcher(objectId);
        if (!m.matches()) {
            throw new InvalidDomainIdentifierException("domainId", "Invalid "
                    + "objectId format.");
        }

        final ObjectId id = new ObjectId(objectId,
                DomainId.getInstance(m.group(1), MetaVersionImpl.valueOf(m.group(2))),
                m.group(3));
        cache.put(objectId, new WeakReference(id));

        return id;
    }

    private ObjectId(String objectId,
            DomainId domainId,
            String objectName)
            throws NullPointerException, IllegalArgumentException {

        this.domainId = domainId;
        this.objectAddress = objectId; //TODO intern?
        this.objectName = objectName;
    }

    public DomainId getDomainId() {
        return domainId;
    }

    public String getAddress() {
        return objectAddress;
    }

    public String getDomainName() {
        return domainId.getName();
    }

    public MetaVersion getDomainVersion() {
        return domainId.getVersion();
    }

    public String getObjectName() {
        return objectName;
    }

    public String asString() {
        return objectAddress;
    }

    @Override
    public String toString() {
        return objectAddress;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.objectAddress);
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
        final ObjectId other = (ObjectId) obj;
        if (!Objects.equals(this.objectAddress, other.objectAddress)) {
            return false;
        }
        return true;
    }

    private static String generateAddress(DomainId domainId, String objectName) {
        return domainId.asString() + OBJECT_SEPARATOR + objectName;
    }

}
