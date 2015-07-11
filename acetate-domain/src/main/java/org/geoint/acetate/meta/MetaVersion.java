package org.geoint.acetate.meta;

import java.util.Optional;
import org.geoint.acetate.domain.annotation.Model;
import org.geoint.acetate.domain.model.DomainModel;

/**
 * Version metadata.
 *
 * A version is similar to that of a Maven version identifier, however, the
 * qualifier is considered a required component of the version and is considered
 * when calculating {@link VersionRange ranges}.
 */
@Model(name = "version",
        domainName = DomainModel.ACETATE_DOMAIN_NAME,
        domainVersion = DomainModel.ACETATE_DOMAIN_VERSION)
public interface MetaVersion {

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
    boolean isWithin(MetaVersion v);

}
