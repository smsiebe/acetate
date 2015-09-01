/*
 * Copyright 2015 geoint.org.
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
package org.geoint.acetate.domain.entity;

import org.geoint.acetate.annotation.Domain;

/**
 *
 * @author steve_siebert
 * @param <E>
 */
@Domain(name="entityBuilder")
public interface EntityBuilder<E> {

    /**
     * Create a transient instance of the entity.
     *
     * @return transient instance of the entity
     * @throws EntityException thrown if there were problems with the entity
     * state
     */
    E build() throws EntityException;
}
