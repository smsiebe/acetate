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
package org.geoint.acetate.java.model.reflect;

import java.util.Optional;
import org.geoint.acetate.java.model.TypeClass;
import org.geoint.acetate.java.model.MapClassRef;

/**
 *
 * @author steve_siebert
 * @param <K> key class model
 * @param <KM> type model of key
 * @param <V> value class model
 * @param <VM> type model of value
 */
public class ReflectedMapClass<K, KM extends TypeClass<K>, V, VM extends TypeClass<V>>
        implements MapClassRef<K, KM, V, VM> {

    private final String name;
    private final Optional<String> description;
    private final KM keyType;
    private final VM valueType;

    public ReflectedMapClass(String name, Optional<String> description,
            KM keyType, VM valueType) {
        this.name = name;
        this.description = description;
        this.keyType = keyType;
        this.valueType = valueType;
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
    public Class<K> getKeyClass() {
        return keyType.getDomainClass();
    }

    @Override
    public KM getKeyModel() {
        return keyType;
    }

    @Override
    public Class<V> getValueClass() {
        return valueType.getDomainClass();
    }

    @Override
    public VM getValueModel() {
        return valueType;
    }

}
