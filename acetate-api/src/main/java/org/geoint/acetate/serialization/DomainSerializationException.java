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

import org.geoint.acetate.model.ResourceModel;

/**
 * Thrown if there was a problem serializing or deserializing a resource.
 *
 * @author steve_siebert
 */
public class DomainSerializationException extends RuntimeException {

    public DomainSerializationException(String message) {
        super(message);
    }

    public DomainSerializationException(Throwable cause) {
        super(cause);
    }

    public DomainSerializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DomainSerializationException(SerializationFormat format,
            Class<?> serializedType) {
        super(message(format, serializedType, null));
    }

    public DomainSerializationException(SerializationFormat format,
            Class<?> serializedType, String msg) {
        super(message(format, serializedType, msg));
    }

    public DomainSerializationException(SerializationFormat format,
            Class<?> serializedType, String msg, Throwable ex) {
        super(message(format, serializedType, msg), ex);
    }

    public DomainSerializationException(SerializationFormat format,
            Class<?> serializedType, Throwable ex) {
        super(message(format, serializedType, null), ex);
    }

    public DomainSerializationException(ResourceModel resource,
            SerializationFormat format, Class<?> serializer) {
        super(message(resource, format, serializer, null));
    }

    public DomainSerializationException(ResourceModel resource,
            SerializationFormat format, Class<?> serializer, String message) {
        super(message(resource, format, serializer, message));
    }

    public DomainSerializationException(ResourceModel resource,
            SerializationFormat format, Class<?> serializer, String message,
            Throwable cause) {
        super(message(resource, format, serializer, message), cause);
    }

    public DomainSerializationException(ResourceModel resource,
            SerializationFormat format, Class<?> serializer, Throwable cause) {
        super(message(resource, format, serializer, null), cause);
    }

    private static String message(ResourceModel resource,
            SerializationFormat format, Class<?> serializer, String msg) {
        return String.format("Resource '%s' could not be formatted as '%s' "
                + "with the serializer. '%s' ",
                resource.toString(), format.asContentType(), serializer.getName(),
                String.valueOf(msg));
    }

    private static String message(SerializationFormat format,
            Class<?> serializedType, String msg) {
        return String.format("Unable to complete serialization for java class "
                + "'%s' for format '%s'. %s", serializedType.getName(),
                format.asContentType(), String.valueOf(msg));
    }
}
