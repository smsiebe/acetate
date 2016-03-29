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
package org.geoint.acetate.java.model;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.geoint.acetate.model.MapRef;

/**
 * Domain model for a {@link Map java.util.Map}.
 *
 * @author steve_siebert
 * @param <K> key model
 * @param <V> value model
 */
public class MapClassRef<K, V>
        implements MapRef<TypeClassRef<? extends TypeClass<K>>, TypeClassRef<? extends TypeClass<V>>> {

    private final String name;
    private final Optional<String> description;
    private final TypeClassRef<? extends TypeClass<K>> keyRef;
    private final TypeClassRef<? extends TypeClass<V>> valueRef;

    public MapClassRef(String name, String description,
            TypeClassRef<? extends TypeClass<K>> keyRef,
            TypeClassRef<? extends TypeClass<V>> valueRef) {
        this.name = name;
        this.description = Optional.ofNullable(description);
        this.keyRef = keyRef;
        this.valueRef = valueRef;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Optional<String> getDescription() {
        return description;
    }

    @Override
    public TypeClassRef<? extends TypeClass<K>> getKeyRef() {
        return keyRef;
    }

    public TypeClass<K> getKeyModel() {
        return keyRef.getReferencedType();
    }

    /**
     * Class for the key type.
     *
     * @return key class
     */
    public Class<K> getKeyClass() {
        return getKeyModel().getDomainClass();
    }

    @Override
    public TypeClassRef<? extends TypeClass<V>> getValueRef() {
        return valueRef;
    }

    public TypeClass<V> getValueModel() {
        return valueRef.getReferencedType();
    }

    /**
     * Class for the value type.
     *
     * @return value class
     */
    public Class<V> getValueClass() {
        return getValueModel().getDomainClass();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.name);
        hash = 37 * hash + Objects.hashCode(this.keyRef);
        hash = 37 * hash + Objects.hashCode(this.valueRef);
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
        final MapClassRef<?, ?> other = (MapClassRef<?, ?>) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.keyRef, other.keyRef)) {
            return false;
        }
        if (!Objects.equals(this.valueRef, other.valueRef)) {
            return false;
        }
        return true;
    }

}
