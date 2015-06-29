package org.geoint.acetate.impl.meta.model;

import org.geoint.acetate.meta.VersionQualifier;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.geoint.acetate.meta.MetaVersion;

public final class ModelVersion implements Serializable, MetaVersion,
        Comparable<ModelVersion> {

    private static final long serialVersionUID = 1L;
    private final int majorVersion;
    private final int minorVersion;
    private final Optional<Integer> increment;
    private final VersionQualifier qualifier;
    private final Optional<Integer> buildNumber;
    private static final String VERSION_SEPARATOR = ".";
    private static final String QUALIFIER_SEPARATOR = "-";
    static final Pattern VERSION_PATTERN
            = Pattern.compile("(\\d+?)\\.(\\d+?)((\\.)(\\d*?))?\\-(\\w*)((\\-)(\\d+))?");

    public ModelVersion(int majorVersion,
            int minorVersion,
            VersionQualifier qualifier) {
        this(majorVersion, minorVersion, null, qualifier, null);
    }

    public ModelVersion(int majorVersion,
            int minorVersion,
            int increment,
            VersionQualifier qualifier) {
        this(majorVersion, minorVersion, increment, qualifier, null);
    }

    public ModelVersion(int majorVersion,
            int minorVersion,
            VersionQualifier qualifier,
            int buildNumber) {
        this(majorVersion, minorVersion, null, qualifier, buildNumber);
    }

    public ModelVersion(int majorVersion,
            int minorVersion,
            Integer increment,
            VersionQualifier qualifier,
            Integer buildNumber) {
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
        this.increment = Optional.ofNullable(increment);
        this.qualifier = qualifier;
        this.buildNumber = Optional.ofNullable(buildNumber);
    }

    /**
     * Parses the version string.
     * <p>
     * The format of the version string must be in the following form:
     *
     * {@code major.minor[.increment]-QUALIFIER[-buildNumber] }
     * <p>
     * Where major and minot are required integers, increment is an optional
     * integer, qualifier is a required string representation of a
     * {@link VersionQualifier} and build number is an optional long.
     *
     * @param version
     * @return
     * @throws IllegalArgumentException if the version string is not in a valid
     * format
     *
     */
    public static ModelVersion valueOf(String version)
            throws IllegalArgumentException {

        Matcher m = VERSION_PATTERN.matcher(version);
        if (!m.find()) {
            throw new IllegalArgumentException("Unsupported version format for "
                    + "version '" + version + "'");
        }

        final VersionQualifier qual;
        try {
            qual = (m.group(6) != null)
                    ? VersionQualifier.valueOf(m.group(6))
                    : null;
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Unknown version qualifier type "
                    + "'" + m.group(6) + "'.", ex);
        }

        return new ModelVersion(
                Integer.valueOf(m.group(1)), //major
                Integer.valueOf(m.group(2)), //minor
                (m.group(5) == null) ? null : Integer.valueOf(m.group(5)), //inc
                qual,
                (m.group(9) == null) ? null : Integer.valueOf(m.group(9)) //build
        );
    }

    @Override
    public int getMajorVersion() {
        return majorVersion;
    }

    @Override
    public int getMinorVersion() {
        return minorVersion;
    }

    @Override
    public Optional<Integer> getIncrement() {
        return increment;
    }

    @Override
    public VersionQualifier getQualifier() {
        return qualifier;
    }

    @Override
    public Optional<Integer> getBuildNumber() {
        return buildNumber;
    }

    @Override
    public int compareTo(ModelVersion o) {

        //major version
        if (this.majorVersion > o.majorVersion) {
            return 1;
        } else if (this.majorVersion < o.majorVersion) {
            return -1;
        }

        //minor version
        if (this.minorVersion > o.minorVersion) {
            return 1;
        } else if (this.minorVersion < o.minorVersion) {
            return -1;
        }

        //increment
        final int incrementComapre
                = compareVersionComponent(this.increment, o.increment);
        if (incrementComapre != 0) {
            return incrementComapre;
        }

        //qualifier
        final int qualifierCompare
                = compareVersionComponent(this.qualifier.ordinal(),
                        this.qualifier.ordinal());
        if (qualifierCompare != 0) {
            return qualifierCompare;
        }

        //build number
        final int buildCompare
                = compareVersionComponent(this.buildNumber, o.buildNumber);
        if (buildCompare != 0) {
            return buildCompare;
        }

        return 0;
    }

    private int compareVersionComponent(int o1, int o2) {
        if (o1 > o2) {
            return 1;
        } else if (o1 < o2) {
            return -1;
        }
        return 0;
    }

    private int compareVersionComponent(Optional<Integer> o1,
            Optional<Integer> o2) {
        if (o1.isPresent()) {
            if (o1.isPresent()) {
                if (o1.get() > o2.get()) {
                    return 1;
                } else if (o1.get() < o2.get()) {
                    return -1;
                }
            } else {
                return 1;
            }
        } else {
            return -1;
        }
        return 0;
    }

    @Override
    public String asString() {
        StringBuilder sb = new StringBuilder();
        sb.append(majorVersion)
                .append(VERSION_SEPARATOR)
                .append(minorVersion);
        if (increment.isPresent()) {
            sb.append(VERSION_SEPARATOR).append(increment.get());
        }
        sb.append(QUALIFIER_SEPARATOR).append(qualifier);
        if (buildNumber.isPresent()) {
            sb.append(QUALIFIER_SEPARATOR).append(buildNumber.get());
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return asString();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + this.majorVersion;
        hash = 23 * hash + this.minorVersion;
        hash = 23 * hash + Objects.hashCode(this.increment);
        hash = 23 * hash + Objects.hashCode(this.qualifier);
        hash = 23 * hash + Objects.hashCode(this.buildNumber);
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
        final ModelVersion other = (ModelVersion) obj;
        if (this.majorVersion != other.majorVersion) {
            return false;
        }
        if (this.minorVersion != other.minorVersion) {
            return false;
        }
        if (!Objects.equals(this.increment, other.increment)) {
            return false;
        }
        if (this.qualifier != other.qualifier) {
            return false;
        }
        return Objects.equals(this.buildNumber, other.buildNumber);
    }

}
