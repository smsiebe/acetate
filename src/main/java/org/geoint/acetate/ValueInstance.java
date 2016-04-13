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
package org.geoint.acetate;

import java.util.Arrays;
import java.util.Objects;
import org.geoint.acetate.model.ValueType;

/**
 * Instance of a domain value.
 *
 */
public abstract class ValueInstance extends TypeInstance<ValueType> {

    public ValueInstance(ValueType typeModel) {
        super(typeModel);
    }

    /**
     * Return the value as bytes.
     *
     * @return binary form of value
     * @throws DomainInstantiationException
     */
    public abstract byte[] asBytes() throws DomainInstantiationException;

    /**
     * Creates a new ValueInstance backed by the provided byte array.
     * <p>
     * While the ValueInstance object is thread-safe modification of the
     * provided byte array is not synchronized by ValueInstance, and may (will)
     * result in consistency issues. If the provided byte array will be
     * manipulated in a concurrent environment it is recommended to use the
     * {@link #newDefensiveInstance} which will create a defensive copy of the
     * provided byte array, resulting in a fully immutable and thread-safe
     * ValueInstance object (at the cost memory).
     *
     * @param model value domain type
     * @param bytes value content in bytes (no assumed "format")
     * @return new ValueInstance
     */
    public static ValueInstance newInstance(ValueType model, byte[] bytes) {
        return new ByteBackedValueInstance(model, bytes);
    }

    /**
     * Creates a new immutable and thread-safe ValueInstance backed by a
     * defensive copy of the byte array.
     *
     * @param model value domain type
     * @param bytes value content in bytes that will be copied
     * @return new ValueInstance
     */
    public static ValueInstance newDefensiveInstance(ValueType model,
            byte[] bytes) {
        final byte[] defensiveCopy = new byte[bytes.length];
        System.arraycopy(bytes, 0, defensiveCopy, 0, bytes.length);
        return new ByteBackedValueInstance(model, defensiveCopy);
    }

    /**
     * Byte array backed value instance.
     */
    private static final class ByteBackedValueInstance extends ValueInstance {

        private final byte[] bytes;

        private ByteBackedValueInstance(ValueType model, byte[] bytes) {
            super(model);
            this.bytes = bytes;
        }

        @Override
        public byte[] asBytes() {
            return this.bytes;
        }

        @Override
        public String toString() {
            return String.format("Instance of domain value '%s'",
                    typeModel.toString());
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 71 * hash + Objects.hashCode(this.typeModel);
            hash = 71 * hash + Arrays.hashCode(this.bytes);
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
            final ByteBackedValueInstance other = (ByteBackedValueInstance) obj;
            if (!Objects.equals(this.typeModel, other.typeModel)) {
                return false;
            }
            return Arrays.equals(this.bytes, other.bytes);
        }

    }
}
