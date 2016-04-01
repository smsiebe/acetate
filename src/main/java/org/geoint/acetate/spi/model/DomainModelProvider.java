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

import java.util.Set;
import org.geoint.acetate.model.DomainModel;

/**
 * Provides domain metamodel definitions.
 * <p>
 * Implementations discovering available domain models must return complete 
 * domain models.
 * <p>
 * Implementations are not assumed to cache or to be tread-safe.
 * @author steve_siebert
 */
public interface DomainModelProvider {

    /**
     * Return all domain type models known to this provider.
     *
     * @return all resources known by this provider
     */
    Set<DomainModel> getDomainModels();

}
