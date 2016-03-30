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

/**
 * Thrown if a resource serialization format is unknown/not supported.
 *
 * @author steve_siebert
 */
public class UnsupportedFormatException extends RuntimeException {

    public UnsupportedFormatException(String format) {
        super(defaultMessage(format));
    }

    public UnsupportedFormatException(String format, String message) {
        super(message);
    }

    public UnsupportedFormatException(String format, String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedFormatException(String format, Throwable cause) {
        super(defaultMessage(format), cause);
    }

    private static String defaultMessage(String format) {
        return String.format("Resource serialization format '%s' is not "
                + "supported.", format);
    }

}
