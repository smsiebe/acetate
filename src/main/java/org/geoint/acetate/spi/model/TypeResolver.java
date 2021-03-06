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
package org.geoint.acetate.spi.model;

import java.util.Optional;
import org.geoint.acetate.model.DomainType;

/**
 * Provides domain descriptor to type resolution services.
 *
 * @author steve_siebert
 */
@FunctionalInterface
public interface TypeResolver {

    /**
     * Returns the requested type, if known to the resolver.
     *
     * @param namespace domain namespace
     * @param version domain version
     * @param typeName domain type name
     * @return domain type, if known to the resolver
     */
    Optional<DomainType> resolve(String namespace, String version,
            String typeName);
}
