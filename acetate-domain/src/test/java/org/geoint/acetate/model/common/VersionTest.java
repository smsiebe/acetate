package org.geoint.acetate.model.common;

import java.util.Random;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

/**
 * Test the Version class string formatting meets its javadoc
 * specification and parses the formatted string as well.
 */
public class VersionTest {

    /**
     * Test parsing a version string with the minimal components.
     */
    @Test
    public void testParseMinimalVersion() {
        final Version expected = createVersion(false, false);
        final String testString = expected.toString();
        Version result = Version.valueOf(testString);
        testParse(testString, expected, result);
    }

    /**
     * Test parsing a valid version using an optional increment value.
     *
     */
    @Test
    public void testParseWithIncrement() {
        final Version expected = createVersion(true, false);
        final String testString = expected.toString();
        Version result = Version.valueOf(testString);
        testParse(testString, expected, result);
    }

    /**
     * Test parsing a valid version using an optional build number.
     *
     */
    @Test
    public void testParseWithBuild() {
        final Version expected = createVersion(false, true);
        final String testString = expected.toString();
        Version result = Version.valueOf(testString);
        testParse(testString, expected, result);
    }

    /**
     * Test parsing a valid version using all optional version attributes.
     *
     */
    @Test
    public void testParseFull() {
        final Version expected = createVersion(true, true);
        final String testString = expected.toString();
        Version result = Version.valueOf(testString);
        testParse(testString, expected, result);
    }

    /**
     * Test parsing an invalid version that is missing the major component.
     *
     */
    @Test(expected = IllegalArgumentException.class)
    public void testParseMissingMajor() {
        Version.valueOf(".2-GA");
    }

    /**
     * Test parsing an invalid version that is using an invalid major version.
     *
     */
    @Test(expected = IllegalArgumentException.class)
    public void testParseInvalidMajor() {
        Version.valueOf("dfsdf.5-GA");
    }

    /**
     * Test parsing an invalid version using an invalid qualifier.
     *
     */
    @Test(expected = IllegalArgumentException.class)
    public void testParseInvalidQualifier() {
        Version.valueOf("2.2.dasf-GA");
    }

    /*
     * calls testEquals
     */
    private void testParse(String expectedFormat, Version expected, Version result) {

        //this could happen if the version format is changed and the test 
        //didn't get updated...
        assertEquals("test string did not match expected version",
                expectedFormat, expected.toString());

        assertEquals("String representation of version did not match",
                expectedFormat, result.toString());

        testEquals(expected, result);
    }

    private void testEquals(Version expected, Version result) {

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
     * Creates a Version with specified components.
     *
     * @return
     */
    private Version createVersion(final boolean withIncrement,
            final boolean withBuild) {
        Random rand = new Random();
        VersionQualifier[] quals = VersionQualifier.values();

        final VersionQualifier qual = quals[rand.nextInt(quals.length)];
        final Integer increment
                = withIncrement ? Math.abs(rand.nextInt()) : null;
        final Integer build = withBuild ? Math.abs(rand.nextInt()) : null;

        return new Version(
                Math.abs(rand.nextInt()), //major
                Math.abs(rand.nextInt()), //minor
                increment,
                qual,
                build);
    }

}
