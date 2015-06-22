package org.geoint.acetate.meta;

import java.util.Optional;

/**
 * Version metadata.
 *
 * A version is similar to that of a Maven version identifier, however, the
 * qualifier is considered a required component of the version and is considered
 * when calculating {@link VersionRange ranges}.
 */
public interface MetaVersion {

    int getMajorVersion();

    int getMinorVersion();

    Optional<Integer> getIncrement();

    VersionQualifier getQualifier();

    Optional<Integer> getBuildNumber();

    /**
     * As a String, the version takes the following form:
     *
     * {@code major.minor[.increment]-QUALIFIER[-buildNumber] }
     *
     * @return version as string
     */
    String asString();

}
