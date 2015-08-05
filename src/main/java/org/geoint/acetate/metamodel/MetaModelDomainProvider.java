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
package org.geoint.acetate.metamodel;

import java.util.Collection;
import org.geoint.acetate.domain.DomainModel;
import org.geoint.acetate.domain.provider.DomainProvider;
import org.geoint.metamodel.MetaModel;
import org.geoint.metamodel.MetaModels;

/**
 * Provides domain models discovered from a
 * {@link MetaModel metamodel registry}.
 *
 * @author steve_siebert
 */
public class MetaModelDomainProvider implements DomainProvider {

    private final MetaModels metamodelRegistry;
    private Collection<DomainModel> domainModels;

    /**
     * Loads domain models from the metamodels found by the current context
     * ClassLoader.
     */
    public MetaModelDomainProvider() {
        metamodelRegistry = MetaModels.getRegistry();
    }

    /**
     * Loads domain models from the metamodel descriptor found by the provided
     * ClassLoader resources.
     *
     * @param loader loader to check for metamodel descriptor containing domain
     * model components
     */
    public MetaModelDomainProvider(ClassLoader loader) {
        metamodelRegistry = MetaModels.newInstance(loader);
    }

    @Override
    public Collection<DomainModel> getDomainModels() {
        synchronized (metamodelRegistry) {
            if (domainModels == null) {
                domainModels = DomainMetaModel.fromRegistry(metamodelRegistry);
            }
        }
        return domainModels;
    }

}
