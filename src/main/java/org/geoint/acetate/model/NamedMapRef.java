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

import java.util.Objects;

/**
 * Special type model which describes a key/value pair relationship.
 *
 * @author steve_siebert
 * @param <K> model of the key
 * @param <V> model of the value
 */
public class NamedMapRef<K extends NamedTypeRef, V extends NamedRef>
        extends NamedRef {

    private final K keyRef;
    private final V valueRef;

    public NamedMapRef(String name, K keyRef, V valueRef) {
        super(name);
        this.keyRef = keyRef;
        this.valueRef = valueRef;
    }

    public NamedMapRef(String name, String description, K keyRef, V valueRef) {
        super(name, description);
        this.keyRef = keyRef;
        this.valueRef = valueRef;
    }

    /**
     * Key model reference.
     * <p>
     * This method is available for convenience and is functionally equivalent
     * to {@link NamedMapRef#getKeyRef() }.
     *
     * @return key model
     */
    public K getKeyRef() {
        return keyRef;
    }

    /**
     * Value model reference.
     * <p>
     * This method is available for convenience and is functionally equivalent
     * to {@link NamedMapRef#getValueRef() }.
     *
     * @return value model
     */
    public V getValueRef() {
        return valueRef;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.getName());
        hash = 17 * hash + Objects.hashCode(this.keyRef);
        hash = 17 * hash + Objects.hashCode(this.valueRef);
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
        final NamedMapRef<?, ?> other = (NamedMapRef<?, ?>) obj;
        if (!Objects.equals(this.getName(), other.getName())) {
            return false;
        }
        if (!Objects.equals(this.keyRef, other.keyRef)) {
            return false;
        }
        return Objects.equals(this.valueRef, other.valueRef);
    }

}
