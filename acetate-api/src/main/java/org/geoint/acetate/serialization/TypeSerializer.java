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
import java.nio.channels.WritableByteChannel;
import org.geoint.acetate.TypeInstance;

/**
 * Provides serialization functions for a {@link TypeInstance}.
 * 
 * @author steve_siebert
 * @param <T> domain value instance type
 */
public interface TypeSerializer<T extends TypeInstance> {
    
    /**
     * Serializes a java object to the specified format.
     *
     * @param out channel to write the serialized data
     * @param domainType type instance, may not be null
     * @param format serialized format
     * @throws DomainSerializationException if serialization failed
     * @throws IOException if the output channel could not be written to
     */
    void serialize(WritableByteChannel out,
            T domainType,
            SerializationFormat format)
            throws DomainSerializationException, IOException;
}
