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

import org.geoint.acetate.TypeInstance;
import org.geoint.acetate.model.TypeModel;

/**
 * Serialization and deserialization functions for a format/type.
 *
 * @author steve_siebert
 * @param <M> model of the domain value
 * @param <T> domain value instance
 */
public interface TypeCodec<M extends TypeModel, T extends TypeInstance<M>>
        extends TypeSerializer<T>, TypeDeserializer<T> {

    /**
     * Determine if the codec implementation supports this format context.
     *
     * @param model domain value model
     * @param format serialization format
     * @return true if this codec supports serialization/deserialization for
     * this context
     */
    boolean supports(M model, SerializationFormat format);
}
