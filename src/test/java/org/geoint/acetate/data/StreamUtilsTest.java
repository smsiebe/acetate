package org.geoint.acetate.data;

import java.io.StringReader;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class StreamUtilsTest {

    /**
     * Test fully reading a String.
     *
     * @throws Exception
     */
    @Test
    public void testReadString() throws Exception {
        final String EXPECTED_STRING = "this is a test string.";
        Readable reader = new StringReader(EXPECTED_STRING);
        final String result = StreamUtils.readString(reader);

        assertEquals(EXPECTED_STRING, result);

    }
}
