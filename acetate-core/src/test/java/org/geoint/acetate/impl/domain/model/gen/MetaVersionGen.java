package org.geoint.acetate.impl.domain.model.gen;

import org.geoint.acetate.impl.domain.model.MetaVersionImpl;
import org.geoint.acetate.meta.MetaVersion;
import org.geoint.acetate.meta.VersionQualifier;

/**
 * Mock meta version generator.
 */
public class MetaVersionGen {

    /**
     * Generates a valid MetaVersionImpl instance, which is the same version as
     * the String returned from {@link #generateValid() }.
     *
     * @return
     */
    public static MetaVersion generateValid() {
        return new MetaVersionImpl(
                Integer.valueOf(ModelVersionBuilder.VALID_MAJOR),
                Integer.valueOf(ModelVersionBuilder.VALID_MINOR),
                VersionQualifier.BETA);
    }

    /**
     * Create valid version string.
     *
     * @return valid version string
     */
    public static String generateValidString() {
        return builder()
                .withValidMajor()
                .withValidMinor()
                .withValidQualifier()
                .buildString();
    }

    /**
     * Create an invalid version string.
     *
     * @return invalid version string
     */
    public static String generateInvalidString() {
        return builder()
                .withInvalidMajor()
                .withInvalidMinor()
                .withInvalidQualifier()
                .buildString();
    }

    /**
     * Create a custom meta version string.
     *
     * @return
     */
    public static ModelVersionBuilder builder() {
        return new ModelVersionBuilder();
    }

    public static class ModelVersionBuilder {

        protected String majorVersion;
        protected String minorVersion;
        protected String increment;
        protected String buildNumber;
        protected String qualifier;

        public static final String VALID_MAJOR = "1";
        public static final String INVALID_MAJOR = "invalid";
        public static final String VALID_MINOR = "1";
        public static final String INVALID_MINOR = "invalid";
        public static final String VALID_INCREMENT = "1";
        public static final String INVALID_INCREMENT = "invalid";
        public static final String VALID_BUILD = "1";
        public static final String INVALID_BUILD = "foo";
        public static final String VALID_QUALIFIER = VersionQualifier.BETA.name();
        public static final String INVALID_QUALIFIER = "invalidQualifer";

        public String buildString() {
            StringBuilder sb = new StringBuilder();
            sb.append(majorVersion)
                    .append(".")
                    .append(minorVersion);
            if (increment != null) {
                sb.append(".").append(increment);
            }
            sb.append("-").append(qualifier);
            if (buildNumber != null) {
                sb.append("-").append(buildNumber);
            }
            return sb.toString();
        }

        public ModelVersionBuilder withValidMajor() {
            majorVersion = VALID_MAJOR;
            return this;
        }

        public ModelVersionBuilder withValidMinor() {
            minorVersion = VALID_MINOR;
            return this;
        }

        public ModelVersionBuilder withValidIncrement() {
            increment = VALID_INCREMENT;
            return this;
        }

        public ModelVersionBuilder withValidBuild() {
            buildNumber = VALID_BUILD;
            return this;
        }

        public ModelVersionBuilder withValidQualifier() {
            qualifier = VALID_QUALIFIER;
            return this;
        }

        public ModelVersionBuilder withInvalidMajor() {
            majorVersion = INVALID_MAJOR;
            return this;
        }

        public ModelVersionBuilder withInvalidMinor() {
            minorVersion = INVALID_MINOR;
            return this;
        }

        public ModelVersionBuilder withInvalidIncrement() {
            increment = INVALID_INCREMENT;
            return this;
        }

        public ModelVersionBuilder withInvalidBuild() {
            buildNumber = INVALID_BUILD;
            return this;
        }

        public ModelVersionBuilder withInvalidQualifier() {
            qualifier = INVALID_QUALIFIER;
            return this;
        }


    }

}
