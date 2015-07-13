package org.geoint.acetate.impl.domain.model;

import java.io.Serializable;
import java.util.Optional;
import org.geoint.acetate.model.ModelVersion;
import org.geoint.acetate.model.VersionQualifier;

/**
 * Defines an [un]bounded {@link ModelVersion} range.
 * <p>
 * A version range can be arbitrarily complex, allowing open bounds as well as
 * multiple, distinct, ranges.
 *
 * <h1>String representation</h1>
 * Version ranges can be represented as a String using a shorthand format to
 * indicate range boundary concepts such as exclusive, inclusive, and open
 * boundary/no limit.
 */
public final class MetaVersionRange
        implements ModelVersion, Serializable, Comparable<ModelVersion> {

    private final static long serialVersionUID = 1L;

//    private final RangeBound[] versions;
//
//    /**
//     * Determine if the provided Version is included in this range.
//     *
//     * @param version version to check if it's included in this range
//     * @return true if the version falls within this range
//     */
//    public boolean within(Version version) {
//
//    }
//
//    private interface RangeBound {
//
//        boolean includes(Version version);
//
//    }
//
//    /**
//     * Defines a version range boundary.
//     */
//    private class RangeStatement implements RangeBound {
//
//        private final boolean startExplicit;
//        /*
//         * start version of the range - can be null
//         */
//        private final Version startVersion;
//
//        private final boolean endExplicit;
//        /*
//         * end version of the range - can be null
//         */
//        private final Version endVersion;
//
//        public RangeStatement(boolean startExplicit, Version startVersion, 
//                boolean endExplicit, Version endVersion) {
//            this.startExplicit = startExplicit;
//            this.startVersion = startVersion;
//            this.endExplicit = endExplicit;
//            this.endVersion = endVersion;
//        }
//
//        @Override
//        public boolean includes(Version version) {
//            
//        }
//
//        
//    }
//
//    /**
//     * Identifies a single version to include.
//     */
//    private class ExplicitVersion implements RangeBound {
//
//        private final Version version;
//
//        public ExplicitVersion(Version version) {
//            this.version = version;
//        }
//
//        @Override
//        public boolean includes(Version version) {
//            return this.version.equals(version);
//        }
//
//    }
    /**
     * Returns the major version component of the highest version in the range.
     *
     * @return major version component of the highest version in the range
     */
    @Override
    public int getMajorVersion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    /**
     * Returns the minor version component of the highest version in the range.
     *
     * @return minor version component of the highest version in the range
     */
    @Override
    public int getMinorVersion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    /**
     * Returns the increment version component of the highest version in the
     * range.
     *
     * @return increment version component of the highest version in the range
     */
    @Override
    public Optional<Integer> getIncrement() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    /**
     * Returns the build number component of the highest version in the range.
     *
     * @return build number component of the highest version in the range
     */
    @Override
    public Optional<Integer> getBuildNumber() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    /**
     * Returns the qualifier component of the highest version in the range.
     *
     * @return qualifier component of the highest version in the range
     */
    @Override
    public VersionQualifier getQualifier() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public String asString() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public int compareTo(ModelVersion o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public boolean isWithin(ModelVersion v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
