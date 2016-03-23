/*
 * Copyright 2016 geoint.org.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.geoint.acetate.serialization;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test SerializationFormat factory methods.
 *
 * @author steve_siebert
 */
public class SerializationFormatTest {

    /**
     * Test parsing valid RFC 1341 content type without parameters.
     *
     */
    @Test
    public void testParseContentTypeNoParam() {
        final String contentType = "text/html";

        SerializationFormat f = SerializationFormat.contentType(contentType);

        assertEquals("text", f.getCategory());
        assertEquals("html", f.getType());
        assertEquals(0, f.getParameterCount());
    }

    /**
     * Test parsing a valid RFC 1341 content type with a single parameter.
     */
    @Test
    public void testParseContentTypeSingleParam() {
        final String contentType = "text/html;level=1";

        SerializationFormat f = SerializationFormat.contentType(contentType);

        assertEquals("text", f.getCategory());
        assertEquals("html", f.getType());
        assertEquals(1, f.getParameterCount());
        assertEquals("1", f.findParameter("level").get());
    }

    /**
     * Test parsing a valid RFC 1341 content type with multiple parameters.
     */
    @Test
    public void testParseContentTypeMultiParam() {
        final String contentType = "text/html;level=1;foo=bar";

        SerializationFormat f = SerializationFormat.contentType(contentType);

        assertEquals("text", f.getCategory());
        assertEquals("html", f.getType());
        assertEquals(2, f.getParameterCount());
        assertEquals("1", f.findParameter("level").get());
        assertEquals("bar", f.findParameter("foo").get());
    }

    /**
     * Test parsing a valid RFC 1341 content type with special characters in the
     * parameter value.
     */
    @Test
    public void testParseContentTypeParamValueSpecialChar() {
        final String contentType = "text/html;foo=\"@stuff\"";

        SerializationFormat f = SerializationFormat.contentType(contentType);

        assertEquals("text", f.getCategory());
        assertEquals("html", f.getType());
        assertEquals(1, f.getParameterCount());
        assertEquals("@stuff", f.findParameter("foo").get());
    }

    /**
     * Test parsing a RFC 1341 formatted string with an invalid type.
     */
    @Test(expected = UnsupportedFormatException.class)
    public void testParseContentTypeInvalidType() {
        SerializationFormat.contentType("foo/things");
    }

    /**
     * Test parsing and invalid RFC 1341 content type where the type is missing.
     *
     */
    @Test(expected = UnsupportedFormatException.class)
    public void testParseContentTypeNoType() {
        SerializationFormat.contentType("/text");
    }

    /**
     * Test parsing an invalid RFC 1341 content type where the sub-type is
     * missing.
     */
    @Test(expected = UnsupportedFormatException.class)
    public void testParseContentTypeNoSub() {
        SerializationFormat.contentType("text/");
    }

    /**
     * Test formatting a valid content type formats as expected.
     */
    @Test
    public void testFormatContentType() {

        final String EXPECTED_FORMAT = "text/html;level=\"1\";foo=\"bar\"";
        final Map<String, String> params = new HashMap<>();
        params.put("level", "1");
        params.put("foo", "bar");
        SerializationFormat f = new SerializationFormat("text", "html", params);

        assertEquals(EXPECTED_FORMAT, f.asContentType());
    }

    /**
     * Test that a format with an unacceptable category produces the expected
     * "default" RFC 1341 content type string.
     */
    @Test
    public void testFormatNonContentType() {
        final String EXPECTED_FORMAT = "application/octet-stream";
        assertEquals(EXPECTED_FORMAT, new SerializationFormat("stuff", "things")
                .asContentType()
        );
    }
}
