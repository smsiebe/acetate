package org.geoint.acetate.model.gen;

import org.geoint.acetate.model.gen.DomainIdGen.DomainIdBuilder;

/**
 *
 */
public class ObjectIdGen {

    public static final String VALID_OBJECT_NAME = "My Test Object";
    public static final String INVALID_OBJECT_NAME = "";

    public static String generateValueObjectName() {
        return VALID_OBJECT_NAME;
    }

    public static String generateValidString() {
        return builder()
                .withValidObjectName()
                .withValidDomainName()
                .withValidMajor()
                .withValidMinor()
                .withValidQualifier()
                .buildString();
    }

    public static ObjectIdBuilder builder() {
        return new ObjectIdBuilder();
    }

    public static class ObjectIdBuilder extends DomainIdBuilder {

        private String objectName;

        public ObjectIdBuilder withObjectName(String name) {
            this.objectName = name;
            return this;
        }

        public ObjectIdBuilder withValidObjectName() {
            this.objectName = VALID_OBJECT_NAME;
            return this;
        }

        public ObjectIdBuilder withInvalidObjectName() {
            this.objectName = INVALID_OBJECT_NAME;
            return this;
        }

        @Override
        public String buildString() {
            StringBuilder sb = new StringBuilder();
            sb.append(super.buildString())
                    .append(':')
                    .append(objectName);
            return sb.toString();
        }
    }

}
