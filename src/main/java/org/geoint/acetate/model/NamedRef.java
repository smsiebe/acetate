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

import java.util.Optional;

/**
 * Contextually named reference.
 *
 * @author steve_siebert
 */
public abstract class NamedRef {

    private final String name;
    private final Optional<String> description;

    public NamedRef(String name) {
        this(name, null);
    }

    public NamedRef(String name, String description) {
        this.name = name;
        this.description = Optional.ofNullable(description);
    }

    /**
     * The domain type reference name.
     *
     * @return type reference name
     */
    public String getName() {
        return name;
    }

    /**
     * Optional reference description.
     *
     * @return reference use description
     */
    public Optional<String> getDescription() {
        return description;
    }

}
