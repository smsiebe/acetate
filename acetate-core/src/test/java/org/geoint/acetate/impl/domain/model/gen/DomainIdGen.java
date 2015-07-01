package org.geoint.acetate.impl.domain.model.gen;

import org.geoint.acetate.impl.domain.model.gen.MetaVersionGen.ModelVersionBuilder;

/**
 * Mock domain id generator.
 */
public class DomainIdGen {

    public static String generateValidString() {
        return builder()
                .withValidDomainName()
                .withValidMajor()
                .withValidMinor()
                .withValidQualifier()
                .build();
    }

    public static String generateInvalidString() {
        return builder()
                .withInvalidDomainName()
                .withValidMajor()
                .withValidMinor()
                .withValidQualifier()
                .build();
    }

    public static DomainIdBuilder builder() {
        return new DomainIdBuilder();
    }

    public static class DomainIdBuilder extends ModelVersionBuilder {

        private String name;
        public static final String VALID_DOMAIN_NAME = "myDomain";
        public static final String INVALID_DOMAIN_NAME = "";

        @Override
        public String build() {
            StringBuilder sb = new StringBuilder();
            if (name != null) {
                sb.append(name);
            }
            sb.append(':')
                    .append(super.build());
            return sb.toString();
        }

        public DomainIdBuilder withValidDomainName() {
            this.name = VALID_DOMAIN_NAME;
            return this;
        }

        public DomainIdBuilder withInvalidDomainName() {
            this.name = INVALID_DOMAIN_NAME;
            return this;
        }

        public DomainIdBuilder withDomainName(String dn) {
            this.name = dn;
            return this;
        }

        public DomainIdBuilder withValidVersion() {
            this.withValidMajor()
                    .withValidMinor()
                    .withValidQualifier();
            return this;
        }

        public DomainIdBuilder withInvalidVersion() {
            this.withInvalidMajor()
                    .withInvalidMinor()
                    .withInvalidQualifier();
            return this;
        }
    }
}
