package org.geoint.acetate.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class DomainIdTest {

    @Test(expected = NullPointerException.class)
    public void testNullDomainName() throws Exception {
        DomainId domainId = new DomainId(null, 0);
    }

    @Test(expected = NullPointerException.class)
    public void testEmptyDomainName() throws Exception {
        DomainId domainId = new DomainId("", 0);
    }

    @Test
    public void testZeroDomainVersion() throws Exception {
        DomainId domainId = new DomainId("test", 0);
    }

    @Test
    public void testPositiveDomainVersion() throws Exception {
        DomainId domainId = new DomainId("test", 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeDomainVersion() throws Exception {
        DomainId domainId = new DomainId("test", -1);
    }

    @Test
    public void testParseProperlyFormatted() throws Exception {
        DomainId domainId = DomainId.valueOf("test:0");
        assertEquals("test", domainId.getName());
        assertEquals(0, domainId.getVersion());
    }

    @Test
    public void testParseDomainNameWithColon() throws Exception {
        DomainId domainId = DomainId.valueOf("te:st:0");
        assertEquals("te:st", domainId.getName());
        assertEquals(0, domainId.getVersion());
    }

    @Test(expected = InvalidDomainIdentifierException.class)
    public void testParseInvalidVersion() throws Exception {
        DomainId domainId = DomainId.valueOf("test:-1");
    }

    @Test(expected = NullPointerException.class)
    public void testParseInvalidName() throws Exception {
        DomainId domainId = DomainId.valueOf(":0");
    }
}
