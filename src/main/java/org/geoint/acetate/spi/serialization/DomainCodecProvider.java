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
package org.geoint.acetate.spi.serialization;

import java.util.Optional;
import java.util.ServiceLoader;
import org.geoint.acetate.serialization.SerializationFormat;
import org.geoint.acetate.serialization.TypeCodec;
import org.geoint.acetate.model.DomainType;

/**
 * Provides serialization codecs for a domain value instance.
 * <p>
 * Provider instances made discoverable to a {@link ServiceLoader} will be
 * loaded and will be weighted higher than any system-provided serialization
 * methods for their supported content types.
 *
 * @author steve_siebert
 */
public interface DomainCodecProvider {

    /**
     * Retrieve a resource codec for the requested resource/format.
     *
     * @param type domain type
     * @param format serialization format that must be supported by the codec
     * @return matching codec, or null
     */
    Optional<TypeCodec> getCodecs(DomainType type,
            SerializationFormat format);

}
