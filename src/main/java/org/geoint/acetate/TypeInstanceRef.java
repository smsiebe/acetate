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

/**
 * Provides model driven interface to access domain instance(s) that may compose
 * or relate to another domain instance.
 *
 * @author steve_siebert
 * @param <T> type of the referenced domain instance
 */
public interface TypeInstanceRef<T extends TypeInstance>
        extends NamedInstanceRef<NamedTypeRef> {

    /**
     * Referenced domain type model.
     *
     * @return referenced type
     */
    T getReferencedType();

    /**
     * Provides each instance to the provided consumer.
     *
     * @param instance instance reference
     */
    void forEachType(Consumer<T> instance);

    /**
     * Indicates if the type reference supports multiple instances of the value
     * or just one.
     *
     * @return true if multiple instances are supported, otherwise false
     */
    default boolean isCollection() {
        return getModel().isCollection();
    }

}
