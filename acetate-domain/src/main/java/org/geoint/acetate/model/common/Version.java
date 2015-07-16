package org.geoint.acetate.model.common;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.geoint.acetate.data.DataCodec;
import org.geoint.acetate.domain.annotation.ContentType;
import org.geoint.acetate.domain.annotation.Query;
import org.geoint.acetate.domain.annotation.DataType;
import org.geoint.acetate.domain.annotation.Service;
import org.geoint.acetate.domain.model.DataModel;

/**
 * Version metadata.
 *
 * A version is similar to that of a Maven version identifier, however, the
 * qualifier is considered a required component of the version and is considered
 * when calculating {@link VersionRange ranges}.
 */
@DataType(domain = DataModel.COMMON_DOMAIN_NAME,
        version = DataModel.ACETATE_DOMAIN_VERSION,
        name = "version",
        displayName = "Version")
public final class Version implements Comparable<Version> {

    private static final long serialVersionUID = 1L;

    /**
     * Default domain version used if not defined.
     */
    public static final Version DEFAULT_VERSION
            = new Version(1, 0, VersionQualifier.DEV);

    private final int majorVersion;
    private final int minorVersion;
    private final Optional<Integer> increment;
    private final VersionQualifier qualifier;
    private final Optional<Integer> buildNumber;
    private static final String VERSION_SEPARATOR = ".";
    private static final String QUALIFIER_SEPARATOR = "-";
    static final Pattern VERSION_PATTERN
            = Pattern.compile("(\\d+?)\\.(\\d+?)((\\.)(\\d*?))?\\-(\\w*)((\\-)(\\d+))?");

    public Version(int majorVersion,
            int minorVersion,
            VersionQualifier qualifier) {
        this(majorVersion, minorVersion, null, qualifier, null);
    }

    public Version(int majorVersion,
            int minorVersion,
            int increment,
            VersionQualifier qualifier) {
        this(majorVersion, minorVersion, increment, qualifier, null);
    }

    public Version(int majorVersion,
            int minorVersion,
            VersionQualifier qualifier,
            int buildNumber) {
        this(majorVersion, minorVersion, null, qualifier, buildNumber);
    }

    public Version(int majorVersion,
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
    public static Version valueOf(String version)
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

        return new Version(
                Integer.valueOf(m.group(1)), //major
                Integer.valueOf(m.group(2)), //minor
                (m.group(5) == null) ? null : Integer.valueOf(m.group(5)), //inc
                qual,
                (m.group(9) == null) ? null : Integer.valueOf(m.group(9)) //build
        );
    }

    /**
     *
     * @return major component of the version
     */
    @Query
    public int getMajorVersion() {
        return majorVersion;
    }

    /**
     *
     * @return minor component of the version
     */
    @Query
    public int getMinorVersion() {
        return minorVersion;
    }

    /**
     *
     * @return version increment number
     */
    @Query
    public Optional<Integer> getIncrement() {
        return increment;
    }

    /**
     *
     * @return version build number
     */
    @Query
    public Optional<Integer> getBuildNumber() {
        return buildNumber;
    }

    /**
     *
     * @return version quality qualifier
     */
    @Query
    public VersionQualifier getQualifier() {
        return qualifier;
    }

    /**
     * As a String, the version takes the following form:
     *
     * {@code major.minor[.increment]-QUALIFIER[-buildNumber] }
     *
     * @return version as string
     */
    @Query
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

    /**
     * Determine if the provided version is supported (falls within) this
     * version.
     *
     * @param v
     * @return true if the provided version falls within/is supported by this
     * version
     */
    @Query
    public boolean isWithin(Version v) {
        return this.equals(v);
    }

    @Override
    public int compareTo(Version o) {

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
    public String toString() {
        return asString();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + this.majorVersion;
        hash = 19 * hash + this.minorVersion;
        hash = 19 * hash + Objects.hashCode(this.increment);
        hash = 19 * hash + Objects.hashCode(this.qualifier);
        hash = 19 * hash + Objects.hashCode(this.buildNumber);
        return hash;
    }

    @Override
    public boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Version other = (Version) obj;
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

    /**
     * Default Version binary converter.
     */
    @Service(name = "versionBinaryCodec",
            domain = DataModel.COMMON_DOMAIN_NAME)
    @ContentType("application/binary")
    public static class VersionBinaryCodec extends DataCodec<Version> {

        /**
         * Size, in bytes, of a version data type.
         */
        public static int BYTES = Integer.BYTES * 5;

        public void convert(Version obj, WritableByteChannel channel)
                throws IOException {
            ByteBuffer buff = ByteBuffer.allocate(BYTES);
            buff.putInt(obj.majorVersion)
                    .putInt(obj.minorVersion)
                    .putInt(optionalIntToInt(obj.increment))
                    .putInt(obj.qualifier.ordinal())
                    .putInt(optionalIntToInt(obj.buildNumber));
            channel.write(buff);
        }

        public Version invert(ReadableByteChannel channel)
                throws IOException {
            ByteBuffer buff = readAll(channel, BYTES);
            return new Version(buff.getInt(), //major
                    buff.getInt(), //minor
                    intToOptionalInteger(buff), //increment 
                    VersionQualifier.values()[buff.getInt()],
                    intToOptionalInteger(buff));  //build number
        }

        private int optionalIntToInt(Optional<Integer> optional) {
            return (optional.isPresent()) ? optional.get() : -1;
        }

        /**
         * Returns an Integer if != -1, otherwise returns null.
         *
         * @param buffer
         * @return null if integer was -1, otherwise Integer value
         */
        private Integer intToOptionalInteger(ByteBuffer buffer) {
            int i = buffer.getInt();
            return (i == -1) ? null : i;
        }

        /**
         * Reads all
         *
         * @param channel
         * @param numBytes
         * @return
         * @throws IOException
         */
        private ByteBuffer readAll(ReadableByteChannel channel, int numBytes)
                throws IOException {
            ByteBuffer buff = ByteBuffer.allocate(numBytes);
            int read = 0;
            while ((read = channel.read(buff)) != -1 || buff.remaining() > 0);
            if (read == -1 && buff.remaining() > 0) {
                throw new IOException(String.format("Channel does not contain "
                        + "the expected number of bytes (%d$)", numBytes));
            }
            return buff;
        }
    }
}
