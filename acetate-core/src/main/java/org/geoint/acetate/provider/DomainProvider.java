/*
 * Copyright 2015 Expression project.organization is undefined on line 4, column 57 in Templates/Licenses/license-apache20.txt..
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
package org.geoint.acetate.provider;

import java.util.Collection;
import java.util.ServiceLoader;
import org.geoint.acetate.DomainModel;

/**
 * Loads one or more {@link DomainModel} instances.
 *
 * DomainProvider instances are discovered and instantiated using a
 * {@link ServiceLoader}.
 *
 * @author steve_siebert
 * @see ServiceLoader
 */
@FunctionalInterface
public interface DomainProvider {

    /**
     * Load domain models.
     *
     * @return domain models discovered by the provider
     */
    Collection<DomainModel> getDomainModels();
}
