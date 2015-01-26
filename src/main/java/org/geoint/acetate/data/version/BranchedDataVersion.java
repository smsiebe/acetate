package org.geoint.acetate.data.version;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.UUID;
import org.geoint.terpene.data.DataVersion;
import org.geoint.terpene.data.annotation.Bind;

/**
 * A data structure version that supports branching of the data.
 * <p>
 * A branched data structure is an data structure that has been "forked"
 * (copied) and manipulated outside of the original data structure. Changes can
 * take place on this branched copy and, if desired, merged back with the
 * original data structure - merging all changes as a single atomic transaction.
 *
 */
public class BranchedDataVersion extends DataVersion {

    private final int masterVersion;
    private final String branch;
    private final ZonedDateTime branchDateTime;
    private final String ownerGuid;

    /**
     * Used by terpene-data to bind data.
     *
     * @param masterVersion version of the data structures master branch prior
     * to branching
     * @param version the version of this branch
     * @param branch globally unique branch identifier
     * @param branchDateTime branch creation date time
     * @param branchCreator GUID identifier of the branch owner
     */
    BranchedDataVersion(
            @Bind(name = "masterVersion") int masterVersion,
            @Bind(name = "version") int version,
            @Bind(name = "versionBranch") String branch,
            @Bind(name = "versionBranchDateTime") ZonedDateTime branchDateTime,
            @Bind(name = "versionBranchOwner") String ownerGuid) {
        super(version);
        this.masterVersion = masterVersion;
        this.branch = branch;
        this.branchDateTime = branchDateTime;
        this.ownerGuid = ownerGuid;
    }

    /**
     * Creates a new data structure branch.
     *
     * @param masterVersion version of the master data structure
     * @param branchCreator guid identifier of the branch owner
     */
    BranchedDataVersion(int masterVersion, String ownerGuid) {
        super(0);
        this.masterVersion = masterVersion;
        this.branch = UUID.randomUUID().toString();
        this.branchDateTime = ZonedDateTime.now(ZoneOffset.UTC);
        this.ownerGuid = ownerGuid;
    }

    /**
     * The version of the master branch when this branch was created.
     *
     * @return a version, >=1, of the master branch
     */
    @Bind(name = "masterVersion")
    public int getMasterVersion() {
        return masterVersion;
    }

    /**
     * Globally unique identifier of the branch owner.
     *
     * @return globally unique identifier a string
     */
    @Bind(name = "versionBranchOwner")
    public String getOwnerGuid() {
        return ownerGuid;
    }

    /**
     * Globally unique branch identifier.
     *
     * @return globally unique branch identifier
     */
    @Bind(name = "versionBranch")
    public String getBranch() {
        return branch;
    }

    /**
     * The creation time of the branch.
     *
     * @return the time the branch was created, must be less than or equal to
     * current time
     */
    @Bind(name = "versionBranchDateTime")
    public ZonedDateTime getBranchDateTime() {
        return branchDateTime;
    }

}
