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
    private final String objectId;
    private final String objectName;

    private static final Map<String, WeakReference<ObjectId>> cache
            = new WeakHashMap<>();

    private static final Pattern OBJECTID_PATTERN
            = Pattern.compile(
                    "(" + DomainId.DOMAINID_PATTERN.pattern() + ")"
                    + DomainId.ID_COMPONENT_SEPARATOR
                    + "(.*)$");

    /**
     *
     * @param domainId domain id
     * @param objectName domain-model unique object name
     * @return object id instance
     * @throws NullPointerException throws if domain name, version, or object
     * name was null
     */
    public static ObjectId getInstance(DomainId domainId,
            final String objectName)
            throws NullPointerException {

        if (objectName == null || objectName.isEmpty()) {
            throw new NullPointerException("Object name must not be null or "
                    + "empty.");
        }

        final String upperObjectName = objectName.toUpperCase();
        final String objectId = generateObjectId(domainId, upperObjectName);

        synchronized (cache) {
            if (!cache.containsKey(objectId)) {
                cache.put(objectId, new WeakReference(
                        new ObjectId(objectId, domainId, upperObjectName)
                ));
            }
        }
        return cache.get(objectId).get();
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
            MetaVersion domainVersion, final String objectName)
            throws NullPointerException, IllegalArgumentException {

        return getInstance(
                DomainId.getInstance(domainName, domainVersion),
                objectName.toUpperCase());

    }

    /**
     * Creates an instance from a {@link #asString() formatted string}.
     *
     * @param objectId
     * @return instance id
     * @throws InvalidDomainIdentifierException thrown if the provided string
     * was not formatted properly
     */
    public static ObjectId valueOf(final String objectId)
            throws InvalidDomainIdentifierException {

        final String upperObjectId = objectId.toUpperCase();
        if (cache.containsKey(upperObjectId)) {
            return cache.get(upperObjectId).get();
        }

        Matcher m = OBJECTID_PATTERN.matcher(upperObjectId);
        if (!m.matches()) {
            throw new InvalidDomainIdentifierException("domainId", "Invalid "
                    + "objectId format.");
        }

        final ObjectId id = new ObjectId(upperObjectId,
                DomainId.getInstance(m.group(2), MetaVersionImpl.valueOf(m.group(3))),
                m.group(13));
        cache.put(upperObjectId, new WeakReference(id));

        return id;
    }

    private ObjectId(String objectId,
            DomainId domainId,
            String objectName)
            throws NullPointerException, IllegalArgumentException {

        this.domainId = domainId;
        this.objectId = objectId; //TODO intern?
        this.objectName = objectName;
    }

    public DomainId getDomainId() {
        return domainId;
    }

    public String getAddress() {
        return objectId;
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

    /**
     * Returns the ObejectId in the following format:
     *
     * {@link DomainId#asString()  domain}:{@link ObjectId#getObjectName() object Name}
     *
     * @return object id in controlled format
     */
    public String asString() {
        return objectId;
    }

    @Override
    public String toString() {
        return objectId;
    }

    /**
     *
     * @param domainId
     * @param objectName expected to be upper-case
     * @return formatted object identifier
     */
    private static String generateObjectId(DomainId domainId, String objectName) {
        return domainId.asString() + DomainId.ID_COMPONENT_SEPARATOR + objectName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.domainId);
        hash = 37 * hash + Objects.hashCode(this.objectName);
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
        if (!Objects.equals(this.domainId, other.domainId)) {
            return false;
        }
        if (!Objects.equals(this.objectName, other.objectName)) {
            return false;
        }
        return true;
    }

}
