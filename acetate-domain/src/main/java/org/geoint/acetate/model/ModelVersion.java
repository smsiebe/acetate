package org.geoint.acetate.model;

import java.util.Optional;

/**
 * Version metadata.
 *
 * A version is similar to that of a Maven version identifier, however, the
 * qualifier is considered a required component of the version and is considered
 * when calculating {@link VersionRange ranges}.
 */
public interface ModelVersion {

    /**
     *
     * @return major component of the version
     */
    int getMajorVersion();

    /**
     *
     * @return minor component of the version
     */
    int getMinorVersion();

    /**
     *
     * @return version increment number
     */
    Optional<Integer> getIncrement();

    /**
     *
     * @return version build number
     */
    Optional<Integer> getBuildNumber();

    /**
     *
     * @return version quality qualifier
     */
    VersionQualifier getQualifier();

    /**
     * As a String, the version takes the following form:
     *
     * {@code major.minor[.increment]-QUALIFIER[-buildNumber] }
     *
     * @return version as string
     */
    String asString();

    /**
     * Determine if the provided version is supported (falls within) this
     * version.
     *
     * @param v
     * @return true if the provided version falls within/is supported by this
     * version
     */
    boolean isWithin(ModelVersion v);

}
