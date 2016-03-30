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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Describes a specific serialization format.
 *
 * @author steve_siebert
 */
public class SerializationFormat {

    private static final List<String> RFC_TYPES = Arrays.asList("application",
            "audio", "image", "message", "multipart", "text", "video");

    /**
     * Generic binary RFC 1341 content type.
     */
    public static final String GENERIC_BINARY = "application/octet-stream";
    /**
     * Generic binary content type (uses RFC 1341 content type).
     */
    public static final SerializationFormat GENERIC_BINARY_FORMAT
            = SerializationFormat.contentType(GENERIC_BINARY);
    /**
     * Generic text RFC 1341 content type.
     */
    public static final String GENERIC_TEXT = "text/*";
    /**
     * Generic text format.
     */
    public static final SerializationFormat GENERIC_TEXT_FORMAT
            = SerializationFormat.contentType(GENERIC_TEXT);
    /**
     * Generic JSON RFC 1341 content type.
     */
    public static final String GENERIC_JSON = "application/json";
    /**
     * Generic JSON format.
     */
    public static final SerializationFormat GENERIC_JSON_FORMAT
            = SerializationFormat.contentType(GENERIC_JSON);
    /**
     * Generic XML RFC 1341 content type.
     */
    public static final String GENERIC_XML = "application/xml";
    /**
     * Generic XML format.
     */
    public static final SerializationFormat GENERIC_XML_FORMAT
            = SerializationFormat.contentType(GENERIC_XML);

    /**
     * Format type value which indicates this format defines a "generic"
     * category format.
     */
    public static final String TYPE_WILDCARD = "*";

    private final String category;
    private final String type;
    private final Map<String, String> parameters;

    public SerializationFormat(String category, String type,
            Map<String, String> parameters) {
        this.category = category;
        this.type = type;
        this.parameters = new HashMap(parameters); //defensive copy
    }

    public SerializationFormat(String category, String type) {
        this(category, type, Collections.EMPTY_MAP);
    }

    /**
     * Describe an RFC 1341 ContentType value as a format definition.
     *
     * @param contentType RFC 1341 content category
     * @return format
     */
    public static SerializationFormat contentType(String contentType)
            throws UnsupportedFormatException {

        int typeSplitIndex = contentType.indexOf('/');
        String[] subAndParam = contentType.substring(typeSplitIndex + 1).split(";");
        final String type = contentType.substring(0, typeSplitIndex).toLowerCase();//rfc category name is case insensitve

        if (type == null 
                || typeSplitIndex == -1
                || subAndParam.length == 0
                || subAndParam[0].isEmpty()
                || !RFC_TYPES.contains(type)) {
            throw new UnsupportedFormatException("Format does not appear to "
                    + "be an RFC 1341 content type.");
        }

        return new SerializationFormat(
                type,
                subAndParam[0].toLowerCase(), //rfc subtype name is case insensitive
                Arrays.stream(subAndParam, 1, subAndParam.length)
                .map((p) -> {
                    final int paramSplitIndex = p.indexOf('=');
                    return new String[]{p.substring(0, paramSplitIndex), p.substring(paramSplitIndex + 1)};
                })
                .collect(Collectors.toMap((p) -> p[0].toLowerCase(), //rfc param name is case insensitive
                        (p) -> {
                            String v = p[1];
                            if (v.startsWith("\"")) {
                                return v.substring(1, v.length() - 1);
                            }
                            return v;
                        })//rfc param value is case SENSITIVE
                ));
    }

    /**
     * Format category.
     *
     * @return a category classification of the format
     */
    public String getCategory() {
        return category;
    }

    /**
     * The specific format type within the category.
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Get a specific format parameter value.
     *
     * @param paramName parameter name
     * @return format parameter value, or null
     */
    public Optional<String> findParameter(String paramName) {
        return Optional.ofNullable(parameters.get(paramName));
    }

    /**
     * Get a specific format parameter or return the default value.
     *
     * @param paramName parameter name
     * @param defaultValue default value supplier to use if param isn't set
     * @return format parameter value or the default value
     */
    public String getParameter(String paramName, Supplier<String> defaultValue) {
        return parameters.getOrDefault(paramName, defaultValue.get());
    }

    /**
     * Consumer each format parameter.
     *
     * @param paramConsumer format parameter consumer (name/value)
     */
    public void forEachParameter(BiConsumer<String, String> paramConsumer) {
        parameters.forEach(paramConsumer);
    }

    /**
     * Total number of parameters defined for this format.
     *
     * @return parameter count
     */
    public int getParameterCount() {
        return parameters.size();
    }

    /**
     * Determine if this format is compatible with the provided format.
     *
     * @param other format to compare compatibility
     * @return true if this format is compatible with the provided format
     */
    public boolean isCompatable(SerializationFormat other) {
        //TODO provide a means to provide runtime overriding of this default 
        //implementation (maybe allow providing optional predicates at construction?)
        if (this.equals(other)) {
            return true;
        }

        if (this.getCategory().contentEquals(other.getCategory())) {
            if (this.getType().contentEquals(TYPE_WILDCARD)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Return the serialization format as an RFC 1341 string.
     *
     * @return RFC 1341 Content-Type format
     */
    public String asContentType() {
        StringBuilder sb = new StringBuilder()
                .append((RFC_TYPES.contains(category) || category.startsWith("X-"))
                        ? String.format("%s/%s", category, type)
                        : "application/octet-stream");
        String paramString = parameters.entrySet().stream()
                .map((e) -> String.format("%s=\"%s\"", e.getKey(), e.getValue()))
                .collect(Collectors.joining(";"));
        if (!paramString.isEmpty()) {
            sb.append(";").append(paramString);
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return asContentType();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.category);
        hash = 37 * hash + Objects.hashCode(this.type);
        hash = 37 * hash + Objects.hashCode(this.parameters);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SerializationFormat other = (SerializationFormat) obj;
        if (!Objects.equals(this.category, other.category)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return Objects.equals(this.parameters, other.parameters);
    }

}
