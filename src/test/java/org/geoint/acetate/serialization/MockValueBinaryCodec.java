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
import java.nio.channels.WritableByteChannel;
import org.geoint.acetate.ValueInstance;

/**
 *
 * @author steve_siebert
 */
public class MockValueBinaryCodec implements TypeCodec<ValueInstance> {

    @Override
    public boolean supports(String namespace, String version, String type,
            SerializationFormat format) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void serialize(WritableByteChannel out, ValueInstance domainType,
            SerializationFormat format)
            throws DomainSerializationException, IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ValueInstance deserialize(ReadableByteChannel in,
            SerializationFormat format)
            throws DomainSerializationException, IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
