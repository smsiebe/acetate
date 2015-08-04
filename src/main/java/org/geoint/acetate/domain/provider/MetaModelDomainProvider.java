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
package org.geoint.acetate.domain.provider;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.geoint.acetate.data.Codec;
import org.geoint.acetate.domain.DomainModel;
import org.geoint.acetate.domain.annotation.Accessor;
import org.geoint.acetate.domain.annotation.Description;
import org.geoint.acetate.domain.annotation.DisplayName;
import org.geoint.acetate.domain.annotation.Domain;
import org.geoint.acetate.domain.annotation.Entity;
import org.geoint.acetate.domain.annotation.EntityId;
import org.geoint.acetate.domain.annotation.EntityVersion;
import org.geoint.acetate.domain.annotation.Operation;

/**
 * Loads domain model(s) available to the context class loader using the GEOINT
 * MetaModel framework.
 *
 * @author steve_siebert
 */
public class MetaModelDomainProvider implements DomainProvider {

    private static final Set<Class<?>> DOMAIN_METAMODEL_TYPES
            = setDomainMetaModelClasses(Accessor.class,
                    Description.class, DisplayName.class, Domain.class,
                    Entity.class, EntityId.class, EntityVersion.class,
                    Operation.class, Codec.class);

    @Override
    public Collection<DomainModel> getDomainModels() {
        return Collections.EMPTY_LIST;
    }

    private boolean isDomainMetaModelType(Class<?> metamodelType) {
        return DOMAIN_METAMODEL_TYPES.contains(metamodelType);
    }

    private static Set<Class<?>> setDomainMetaModelClasses(Class<?>... classes) {
        return new HashSet(Arrays.asList(classes));
    }
}
