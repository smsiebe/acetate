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
import org.geoint.acetate.spi.serialization.DomainCodecProvider;

/**
 * Provides domain metamodel definitions.
 * <p>
 * Implementations discovering available domain models must expect that
 * providers may provide domain resources for multiple domains, and multiple
 * providers may provide the same domain resources (same namespace, resource
 * type, and version). Though this scenario is described here, the way this
 * collision is handled is implementation specific.
 *
 * @author steve_siebert
 */
public interface DomainModelProvider extends DomainCodecProvider {

    /**
     * Return all domain type models known to this provider.
     *
     * @return all resources known by this provider
     */
    Set<DomainModel> getDomainModels();

}
