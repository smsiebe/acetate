package org.geoint.acetate.impl.domain.model;

import java.util.Random;
import org.geoint.acetate.model.VersionQualifier;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

/**
 * Test the MetaVersionImpl class string formatting meets its javadoc
 * specification and parses the formatted string as well.
 */
public class MetaVersionTest {

    /**
     * Test parsing a version string with the minimal components.
     */
    @Test
    public void testParseMinimalVersion() {
        final MetaVersionImpl expected = createVersion(false, false);
        final String testString = expected.toString();
        MetaVersionImpl result = MetaVersionImpl.valueOf(testString);
        testParse(testString, expected, result);
    }

    /**
     * Test parsing a valid version using an optional increment value.
     *
     */
    @Test
    public void testParseWithIncrement() {
        final MetaVersionImpl expected = createVersion(true, false);
        final String testString = expected.toString();
        MetaVersionImpl result = MetaVersionImpl.valueOf(testString);
        testParse(testString, expected, result);
    }

    /**
     * Test parsing a valid version using an optional build number.
     *
     */
    @Test
    public void testParseWithBuild() {
        final MetaVersionImpl expected = createVersion(false, true);
        final String testString = expected.toString();
        MetaVersionImpl result = MetaVersionImpl.valueOf(testString);
        testParse(testString, expected, result);
    }

    /**
     * Test parsing a valid version using all optional version attributes.
     *
     */
    @Test
    public void testParseFull() {
        final MetaVersionImpl expected = createVersion(true, true);
        final String testString = expected.toString();
        MetaVersionImpl result = MetaVersionImpl.valueOf(testString);
        testParse(testString, expected, result);
    }

    /**
     * Test parsing an invalid version that is missing the major component.
     *
     */
    @Test(expected = IllegalArgumentException.class)
    public void testParseMissingMajor() {
        MetaVersionImpl.valueOf(".2-GA");
    }

    /**
     * Test parsing an invalid version that is using an invalid major version.
     *
     */
    @Test(expected = IllegalArgumentException.class)
    public void testParseInvalidMajor() {
        MetaVersionImpl.valueOf("dfsdf.5-GA");
    }

    /**
     * Test parsing an invalid version using an invalid qualifier.
     *
     */
    @Test(expected = IllegalArgumentException.class)
    public void testParseInvalidQualifier() {
        MetaVersionImpl.valueOf("2.2.dasf-GA");
    }

    /*
     * calls testEquals
     */
    private void testParse(String expectedFormat, MetaVersionImpl expected, MetaVersionImpl result) {

        //this could happen if the version format is changed and the test 
        //didn't get updated...
        assertEquals("test string did not match expected version",
                expectedFormat, expected.toString());

        assertEquals("String representation of version did not match",
                expectedFormat, result.toString());

        testEquals(expected, result);
    }

    private void testEquals(MetaVersionImpl expected, MetaVersionImpl result) {

        assertEquals("versions did not match",
                expected, result);

        assertEquals("major version invalid",
                expected.getMajorVersion(), result.getMajorVersion());

        assertEquals("minor version invalid",
                expected.getMinorVersion(), result.getMinorVersion());

        assertEquals("invalid qualifier",
                expected.getQualifier(), result.getQualifier());

        if (!expected.getIncrement().isPresent()) {
            assertFalse("increment was expected to be null",
                    result.getIncrement().isPresent());
        } else {
            assertEquals(expected.getIncrement().get(),
                    result.getIncrement().get());
        }

        if (!expected.getBuildNumber().isPresent()) {
            assertFalse("build was expected to be null",
                    result.getBuildNumber().isPresent());
        } else {
            assertEquals(expected.getBuildNumber().get(),
                    result.getBuildNumber().get());
        }

    }

    /**
     * Creates a MetaVersionImpl with specified components.
     *
     * @return
     */
    private MetaVersionImpl createVersion(final boolean withIncrement,
            final boolean withBuild) {
        Random rand = new Random();
        VersionQualifier[] quals = VersionQualifier.values();

        final VersionQualifier qual = quals[rand.nextInt(quals.length)];
        final Integer increment
                = withIncrement ? Math.abs(rand.nextInt()) : null;
        final Integer build = withBuild ? Math.abs(rand.nextInt()) : null;

        return new MetaVersionImpl(
                Math.abs(rand.nextInt()), //major
                Math.abs(rand.nextInt()), //minor
                increment,
                qual,
                build);
    }

}
