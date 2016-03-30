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
public interface ValueCodec
        extends TypeSerializer<ValueInstance>, TypeDeserializer<ValueInstance> {

    public static TypeCodec<ValueInstance> valueCodec(
            String ns, String dv, String tn,
            SerializationFormat supportedFormat,
            TypeSerializer<ValueInstance> serializer,
            TypeDeserializer<ValueInstance> deserializer) {
        return new TypeCodec<ValueInstance>() {

            @Override
            public boolean supports(String namespace, String version,
                    String type, SerializationFormat format) {
                return ns.contentEquals(namespace)
                        && dv.contentEquals(version)
                        && tn.contentEquals(type)
                        && supportedFormat.isCompatable(format);
            }

            @Override
            public void serialize(WritableByteChannel out,
                    ValueInstance domainType, SerializationFormat format)
                    throws DomainSerializationException, IOException {
                serializer.serialize(out, domainType, format);
            }

            @Override
            public ValueInstance deserialize(ReadableByteChannel in,
                    SerializationFormat format)
                    throws DomainSerializationException, IOException {
                return deserializer.deserialize(in, format);
            }

        };
    }

}
