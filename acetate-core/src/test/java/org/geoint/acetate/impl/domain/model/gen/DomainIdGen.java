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
                .buildString();
    }

    public static String generateInvalidString() {
        return builder()
                .withInvalidDomainName()
                .withValidMajor()
                .withValidMinor()
                .withValidQualifier()
                .buildString();
    }

    public static DomainIdBuilder builder() {
        return new DomainIdBuilder();
    }

    public static class DomainIdBuilder extends ModelVersionBuilder {

        private String domainName;
        public static final String VALID_DOMAIN_NAME = "myDomain";
        public static final String INVALID_DOMAIN_NAME = "";

        @Override
        public String buildString() {
            StringBuilder sb = new StringBuilder();
            if (domainName != null) {
                sb.append(domainName);
            }
            sb.append(':')
                    .append(super.buildString());
            return sb.toString();
        }

        public DomainIdBuilder withValidDomainName() {
            this.domainName = VALID_DOMAIN_NAME;
            return this;
        }

        public DomainIdBuilder withInvalidDomainName() {
            this.domainName = INVALID_DOMAIN_NAME;
            return this;
        }

        public DomainIdBuilder withDomainName(String dn) {
            this.domainName = dn;
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
