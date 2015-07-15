package org.geoint.acetate.model;

import org.geoint.acetate.model.gen.DomainIdGen;
import org.geoint.acetate.model.gen.MetaVersionGen;
import org.geoint.acetate.model.gen.ObjectIdGen;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 */
public class ObjectIdTest {

    @Test(expected = NullPointerException.class)
    public void testNullDomain() throws Exception {
        ObjectId objectId = ObjectId.getInstance(null,
                ObjectIdGen.generateValueObjectName());
    }

    @Test(expected = NullPointerException.class)
    public void testNullObjectName() throws Exception {
        ObjectId objectId
                = ObjectId.getInstance(DomainId.DEFAULT_DOMAIN, null);
    }

    @Test
    public void testParseProperlyFormatted() throws Exception {
        ObjectId objectId = ObjectId.valueOf(ObjectIdGen.generateValidString());
        assertEquals(ObjectIdGen.VALID_OBJECT_NAME, objectId.getObjectName());
        assertEquals(DomainIdGen.DomainIdBuilder.VALID_DOMAIN_NAME, objectId.getDomainName());
        assertEquals(DomainIdGen.DomainIdBuilder.VALID_MAJOR, String.valueOf(objectId.getDomainVersion().getMajorVersion()));
        assertEquals(DomainIdGen.DomainIdBuilder.VALID_MINOR, String.valueOf(objectId.getDomainVersion().getMinorVersion()));
        assertEquals(DomainIdGen.DomainIdBuilder.VALID_QUALIFIER, String.valueOf(objectId.getDomainVersion().getQualifier()));
    }

    @Test
    public void testParseDomainNameWithColon() throws Exception {
        final String EXPECTED_DOMAIN_NAME = "my:model:Name";
        ObjectId objectId = ObjectId.valueOf(
                ObjectIdGen.builder()
                .withValidObjectName()
                .withDomainName(EXPECTED_DOMAIN_NAME)
                .withValidMajor()
                .withValidMinor()
                .withValidQualifier()
                .buildString());
        assertEquals(EXPECTED_DOMAIN_NAME, objectId.getDomainName());
        assertEquals(ObjectIdGen.VALID_OBJECT_NAME, objectId.getObjectName());
        assertEquals(MetaVersionGen.generateValid(), objectId.getDomainVersion());
    }

    @Test
    public void testParseObjectNameWithColon() throws Exception {
        final String EXPECTED_OBJECT_NAME = "my:model:Name";
        ObjectId objectId = ObjectId.valueOf(ObjectIdGen.builder()
                .withObjectName(EXPECTED_OBJECT_NAME)
                .withValidDomainName()
                .withValidMajor()
                .withValidMinor()
                .withValidQualifier()
                .buildString());
        assertEquals(EXPECTED_OBJECT_NAME, objectId.getObjectName());
        assertEquals(MetaVersionGen.generateValid(), objectId.getDomainVersion());
    }

}
