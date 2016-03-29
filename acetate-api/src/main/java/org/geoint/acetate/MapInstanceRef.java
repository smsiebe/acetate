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

import java.util.function.BiConsumer;

/**
 * Map instance reference.
 *
 * @author steve_siebert
 * @param <K>
 * @param <V>
 */
public interface MapInstanceRef<K extends TypeInstance, V extends TypeInstance> {

    String getName();

    default K getKey() {
        return getKeyRef().getReferencedType();
    }

    InstanceRef<K> getKeyRef();

    default V getValue() {
        return getValueRef().getReferencedType();
    }

    InstanceRef<V> getValueRef();

    void forEachType(BiConsumer<InstanceRef<K>, InstanceRef<V>> instance);
}
