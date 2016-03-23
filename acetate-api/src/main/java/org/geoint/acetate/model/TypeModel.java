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
 *
 * @author steve_siebert
 */
public interface TypeModel {

    /**
     * Namespace of the domain.
     *
     * @return domain namespace
     */
    String getDomainNamespace();

    /**
     * Domain-unique name for the object type.
     *
     * @return contextual value name
     */
    String getDomainType();

    /**
     * Unique version of the data type.
     *
     * @return value version
     */
    String getDomainVersion();
    
    /**
     * Optional description for the domain type.
     * 
     * @return description
     */
    Optional<String> getDescription();
}
