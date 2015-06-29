package org.geoint.acetate.impl.meta.model;

import java.io.Serializable;

/**
 * Defines a {@link Version} range.
 *
 * A version range can be arbitrarily complex, allowing open bounds as well as
 * multiple range boundries.
 *
 * <h1>String representation</h1>
 * Version ranges can be represented as a String using a shorthand format to
 * indicate range boundary concepts such as exclusive, inclusive, and open
 * boundary/no limit.
 */
public final class VersionRange implements Serializable {

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
}
