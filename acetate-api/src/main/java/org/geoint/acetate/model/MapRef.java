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

/**
 * Special type model which describes a key/value pair relationship.
 *
 * @author steve_siebert
 * @param <K> domain model of the key
 * @param <V> domain model of the value
 */
public interface MapRef<K extends TypeModel, V extends TypeModel>
        extends NamedRef {

    /**
     * Key model.
     * <p>
     * This method is available for convenience and is functionally equivalent
     * to {@link MapRef#getKeyRef() }.
     *
     * @return key model
     */
    public K getKeyModel();

    /**
     * Value model.
     * <p>
     * This method is available for convenience and is functionally equivalent
     * to {@link MapRef#getValueRef() }.
     *
     * @return value model
     */
    public V getValueModel();
}
