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

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.geoint.acetate.domain.DomainModel;
import org.geoint.acetate.domain.annotation.Domain;
import org.geoint.acetate.domain.annotation.Entity;
import org.geoint.acetate.domain.annotation.Event;
import org.geoint.metamodel.MetaModels;
import org.geoint.metamodel.ModelType;

/**
 * Utility class used to load domain models from a {@link MetaModels metamodel
 * registry}.
 *
 * @author steve_siebert
 */
public class DomainMetaModel {

    public static Collection<DomainModel> fromRegistry(MetaModels registry) {
        Map<String, DomainModel> models = new HashMap<>();
        addMetaType(registry, Domain.class, models);
        addMetaType(registry, Entity.class, models);
        addMetaType(registry, Event.class, models);
        return models.values();
    }

    private static void addMetaType(MetaModels registry, 
            Class<? extends Annotation> domainMetaModel,
            Map<String, DomainModel> models) {
        Collection<ModelType<?,?>> domainTypes 
                = registry.getModelTypes(domainMetaModel);
        domainTypes.forEach((t) -> {
            t.get
        });
    }
}
