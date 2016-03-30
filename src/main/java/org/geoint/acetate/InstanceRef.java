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

import java.util.function.Consumer;
import org.geoint.acetate.model.NamedTypeRef;
import org.geoint.acetate.model.DomainType;

/**
 *
 * @author steve_siebert
 * @param <T>
 */
public interface InstanceRef<T extends TypeInstance> {

    NamedTypeRef<? extends DomainType> getRefModel();

    /**
     * Referenced domain type model.
     *
     * @return referenced type
     */
    T getReferencedType();

    void forEachType(Consumer<T> instance);

    /**
     * Indicates if the type reference supports multiple instances of the value
     * or just one.
     *
     * @return true if multiple instances are supported, otherwise false
     */
    default boolean isCollection() {
        return getRefModel().isCollection();
    }

    /**
     * The reference name.
     *
     * @return type reference name
     */
    default String getName() {
        return getRefModel().getName();
    }
}
