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

import java.io.IOException;
import java.nio.channels.ReadableByteChannel;
import org.geoint.acetate.TypeInstance;

/**
 * Deserializes a {@link SerializationFormat formatted} domain type to a
 * domain type instance representation.
 *
 * @author steve_siebert
 * @param <T>  instance type
 */
public interface TypeDeserializer<T extends TypeInstance> {

    /**
     * Deserializes the domain value from the specified format.
     *
     * @param in formatted data source
     * @param format serialization format
     * @return object representation of the data value, may not return null
     * @throws DomainSerializationException if there were problems reading or
     * constructing the value from the channel
     * @throws IOException if thrown by the provided channel
     */
    T deserialize(ReadableByteChannel in,
            SerializationFormat format)
            throws DomainSerializationException, IOException;
}
