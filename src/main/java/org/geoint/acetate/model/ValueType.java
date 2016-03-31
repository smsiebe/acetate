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
package org.geoint.acetate.model;

import org.geoint.acetate.serialization.TypeCodec;

/**
 * Domain value type.
 * <p>
 * A domain value type is analogous to a "data type" in that it represents a
 * single-value type.
 *
 * @author steve_siebert
 */
public final class ValueType extends DomainType {

    private final TypeCodec defaultCharacterCodec;
    private final TypeCodec defaultBinaryCodec;

    public ValueType(String namespace, String version, String name,
            TypeCodec defaultCharCodec, TypeCodec defaultBinCodec)
            throws InvalidModelException {
        this(namespace, version, name, null, defaultCharCodec, defaultBinCodec);
    }

    public ValueType(String namespace, String version, String name,
            String description,
            TypeCodec defaultCharacterCodec, TypeCodec defaultBinaryCodec)
            throws InvalidModelException {
        super(namespace, version, name, description);
        if (defaultCharacterCodec == null) {
            throw new InvalidModelException(String.format("Value types "
                    + "'%s' requires a default character codec.", this.toString()));
        }
        this.defaultCharacterCodec = defaultCharacterCodec;
        if (defaultBinaryCodec == null) {
            throw new InvalidModelException(String.format("Value type "
                    + "'%s' requires a default binary codec.", this.toString()));
        }
        this.defaultBinaryCodec = defaultBinaryCodec;
    }

    public TypeCodec getDefaultCharacterCodec() {
        return defaultCharacterCodec;
    }

    public TypeCodec getDefaultBinaryCodec() {
        return defaultBinaryCodec;
    }

}
