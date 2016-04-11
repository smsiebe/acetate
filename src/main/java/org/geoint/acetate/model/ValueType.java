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

import java.util.Arrays;
import java.util.Objects;
import org.geoint.acetate.DomainInstantiationException;
import org.geoint.acetate.ValueInstance;

/**
 * Domain value type.
 * <p>
 * A domain value type is analogous to a "data type" in that it represents a
 * single-value type.
 *
 * @author steve_siebert
 */
public class ValueType extends DomainType {

    public ValueType(String namespace, String version, String name) {
        super(namespace, version, name);
    }

    public ValueType(String namespace, String version, String name,
            String description) {
        super(namespace, version, name, description);
    }

    public ValueInstance createInstance(byte[] bytes)
            throws DomainInstantiationException {
        return new DefaultValueInstance(this, bytes);
    }

    private class DefaultValueInstance implements ValueInstance {

        private final ValueType model;
        private final byte[] bytes;

        public DefaultValueInstance(ValueType model, byte[] bytes) {
            this.model = model;
            this.bytes = bytes;
        }

        @Override
        public byte[] asBytes() {
            return this.bytes;
        }

        @Override
        public ValueType getModel() {
            return this.model;
        }

        @Override
        public String toString() {
            return model.toString();
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 71 * hash + Objects.hashCode(this.model);
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
            final DefaultValueInstance other = (DefaultValueInstance) obj;
            if (!Objects.equals(this.model, other.model)) {
                return false;
            }
            return Arrays.equals(this.bytes, other.bytes);
        }

    }
}
