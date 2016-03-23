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
import org.geoint.acetate.model.TypeModel;

/**
 *
 * @author steve_siebert
 * @param <KM>
 * @param <K>
 * @param <VM>
 * @param <V>
 */
public interface MapInstanceRef<KM extends TypeModel, K extends TypeInstance<KM>, VM extends TypeModel, V extends TypeInstance<VM>> {

    String getName();

    KM getKeyModel();

    K getKey();

    VM getValueModel();

    V getValue();

    void forEachType(BiConsumer<K, V> instance);
}
