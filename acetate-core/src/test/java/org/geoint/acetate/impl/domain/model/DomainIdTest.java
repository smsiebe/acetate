package org.geoint.acetate.impl.domain.model;

import org.geoint.acetate.impl.domain.model.gen.DomainIdGen;
import org.geoint.acetate.impl.domain.model.gen.MetaVersionGen;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class DomainIdTest {

    @Test(expected = NullPointerException.class)
    public void testNullDomainName() throws Exception {
        DomainId domainId = DomainId.getInstance(null,
                MetaVersionGen.generateValid());
    }

    @Test(expected = NullPointerException.class)
    public void testEmptyDomainName() throws Exception {
        DomainId domainId = DomainId.getInstance("",
                MetaVersionGen.generateValid());
    }

    @Test
    public void testParseProperlyFormatted() throws Exception {
        DomainId domainId = DomainId.valueOf(DomainIdGen.generateValidString());
        assertEquals(DomainIdGen.DomainIdBuilder.VALID_DOMAIN_NAME, domainId.getName());
        assertEquals(MetaVersionGen.generateValid(), domainId.getVersion());
    }

    @Test
    public void testParseDomainNameWithColon() throws Exception {
        final String EXPECTED_DOMAIN_NAME = "my:model:Name";
        DomainId domainId = DomainId.valueOf(DomainIdGen.builder()
                .withDomainName(EXPECTED_DOMAIN_NAME)
                .withValidMajor()
                .withValidMinor()
                .withValidQualifier()
                .build());
        assertEquals(EXPECTED_DOMAIN_NAME, domainId.getName());
        assertEquals(MetaVersionGen.generateValid(), domainId.getVersion());
    }

    @Test(expected = InvalidDomainIdentifierException.class)
    public void testParseInvalidVersion() throws Exception {
        DomainId.valueOf(
                DomainIdGen.builder()
                .withValidDomainName()
                .withInvalidMajor()
                .withInvalidMinor()
                .withValidIncrement()
                .withValidQualifier()
                .build());
    }

    @Test(expected = NullPointerException.class)
    public void testParseInvalidName() throws Exception {
        DomainId.valueOf(DomainIdGen.builder()
                .withInvalidDomainName()
                .withValidVersion()
                .build());
    }
}
