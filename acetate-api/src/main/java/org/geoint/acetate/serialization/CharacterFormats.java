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

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;

/**
 * Format utilities supporting character serialization formats.
 * <p>
 * This utility assumes the use of the {@code charset} parameter defined in the
 * {@link SerializationFormat} to override the default charset.
 * <p>
 * If no charset is specified, the JVM {@link Charset#defaultCharset} is used.
 *
 * @author steve_siebert
 */
public class CharacterFormats {

    /**
     * Parameter name used to set/retrieve the explicit charset of a asString.
     */
    public static final String FORMAT_PARAM_NAME = "charset";

    /**
     * Return the {@link Charset} defined by the asString parameters, or the
     * default JVM Charset.
     *
     * @param format serialization asString
     * @return Charset defined by asString or JVM default
     */
    public static Charset ofOrDefault(SerializationFormat format) {
        try {
            return format.findParameter(FORMAT_PARAM_NAME)
                    .map(Charset::forName)
                    .orElse(Charset.defaultCharset());
        } catch (IllegalCharsetNameException | UnsupportedCharsetException ex) {
            return Charset.defaultCharset();
        }
    }

    /**
     * Returns a newly allocated byte buffer containing the properly encoded
     * string content.
     *
     * @param str string
     * @param format format
     * @return byte buffer containing string content
     */
    public static ByteBuffer asByteBuffer(String str, SerializationFormat format) {
        return ofOrDefault(format).encode(str);
    }

    /**
     * Return a String using the proper codec.
     *
     * @param bytes characters as bytes
     * @param format character format
     * @return properly encoded string
     */
    public static String asString(ByteBuffer bytes, SerializationFormat format) {
        return ofOrDefault(format).decode(bytes).toString();
    }

    /**
     * Attempt to determine if the asString is "binary" or "character" based.
     * <p>
     * This method will only return true if the asString is known to be a
     * character asString, otherwise it returns false.
     *
     * @param format asString to check
     * @return true if known to be a character asString, otherwise false
     */
    public static boolean isCharacterFormat(SerializationFormat format) {
        return SerializationFormat.GENERIC_TEXT_FORMAT.isCompatable(format)
                || format.findParameter(FORMAT_PARAM_NAME).isPresent();
    }
}
