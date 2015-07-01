package org.geoint.acetate.domain.model;

import org.geoint.acetate.domain.annotation.Object;

/**
 * The version qualifier identifies the "completeness" of a component.
 *
 * Terpene can be configured to use of the version qualifier to restrict
 * component versions from being deployed to a platform. This can be used, for
 * example, to ensure that a component that hasn't met certain rigors from
 * accidently being deployed to a production environment.
 */
@Object(name="versionQualifier", domainName="acetate", domainVersion="1.0-BETA")
public enum VersionQualifier {

    /**
     * This version of the release is currently under heavy development.
     *
     * <ul>
     * <li>No APIs are not guaranteed safe and may change at any time.</li>
     * <li>Class may be renamed, moved, or simply deleted.</li>
     * <li>Code isn't even guaranteed to build (though it might).</li>
     * </ul>
     *
     * Terpene components identified as DEV will never deploy on a terpene
     * platform designated as PRODUCTION. You can't even try to configure your
     * platform to do it - don't even try.
     */
    DEV,
    /**
     * The code is released with very limited testing, and equally limited
     * guarantees.
     *
     * Alpha releases are useful to throw between developers to validate
     * contracts, throw out a quick piece of code to verify your work against
     * customer requirements...but not for production. Generally ALPHA releases
     * have the following traits:
     *
     * <ul>
     * <li>Public APIs are not guaranteed safe and may change in later releases.
     * </li>
     * <li>ALPHA releases may regress to DEV releases.</li>
     * <li>Code can change, but generally there won't be dramatic changes to the
     * code -- unless the ALPHA release was off the mark.</li>
     * <li>Code will compile and is expected to run without runtime errors.</li>
     * </ul>
     *
     * By default, ALPHA components cannot be deployed to a terpene platform
     * identified as PRODUCTION. However, Alpha components can be run on a
     * production terpene platform if the default restriction is lowered.
     */
    ALPHA,
    /**
     * BETA code is (no surprise) considered more stable than ALPHA, but not
     * ready for prime time.
     *
     * BETA releases are polished enough that they are often released to
     * customers without a developer/engineer sitting side saddle. BETA releases
     * should be complete with proper logging and be fairly stable.
     *
     * By default, BETA components cannot be deployed to a terpene platform
     * identified as PRODUCTION. However, BETA components can run on a
     * PRODUCTION platform if the default restriction is lowered.
     */
    BETA,
    /**
     * RC, or release candidate, code is really starting to take form as a
     * production piece of code. RC code is expected to pass all unit and
     * integration testing and the code is very stable.
     *
     * <ul>
     * <li>Public APIs are stable and developers should take great pain not to
     * make any backwards incompatible changes after an RC release is made
     * public.</li>
     * <li>Some customers may be offered to run an RC component on their
     * production servers.</li>
     * </ul>
     *
     * By default, RC components CAN run on a PRODUCTION platform. This can be
     * changed by raising the default restriction to GA.
     */
    RC,
    /**
     * The code is Generally Available (full release). Only increment version
     * changes with bug fixes, security patches, and minor features that are
     * guaranteed to be fully backwards compatible should be released after a GA
     * release of a version.
     *
     * GA code will always be deployable on a PRODUCTION platform.
     */
    GA;
}
