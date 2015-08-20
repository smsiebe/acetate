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
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import org.geoint.acetate.domain.DomainModel;
import org.geoint.acetate.domain.DomainType;
import org.geoint.acetate.domain.annotation.Domain;
import org.geoint.acetate.domain.entity.Entity;
import org.geoint.acetate.domain.event.Event;
import org.geoint.metamodel.MetaModelType;
import org.geoint.metamodel.MetaModels;
import org.geoint.metamodel.MetaModel;
import org.geoint.metamodel.ModelType;

/**
 * Utility class used to load domain models from a {@link MetaModels metamodel
 * registry}.
 *
 * @author steve_siebert
 */
public class DomainMetaModel {

    /**
     * Metamodel attribute name for the domain name.
     */
    public static final String DOMAIN_NAME = "acetate.domain.name";
    /**
     * Metamodel attribute name for the domain version.
     */
    public static final String DOMAIN_VERSION = "acetate.domain.version";
    /**
     * Metamodel attribute name for the domain model type name.
     */
    public static final String DOMAIN_TYPE_NAME = "acetate.domain.type.name";

    private static final Pattern PATTERN_DOT_SPLITTER
            = Pattern.compile(".", Pattern.LITERAL);

    public static Collection<DomainModel> fromRegistry(MetaModel registry) {
        Map<String, DomainModel> models = new HashMap<>();  //key=domain name

        addMetaType(registry, Domain.class, models);
        addMetaType(registry, Entity.class, models);
        addMetaType(registry, Event.class, models);
        return models.values();
    }

    private static void addMetaType(MetaModel registry,
            Class<? extends Annotation> domainMetaModel,
            Map<String, DomainModel> models) {
        Collection<ModelType<?, ?>> domainTypes
                = registry.getModelTypes(domainMetaModel);
        domainTypes.forEach((t) -> {
            final String domainName = getDomainName(t);
            final String domainVersion = getDomainVersion(t);
            final String domainType = getTypeName(t);
        });
    }

    private static String getDomainName(MetaModelType model) {
        return getMetaModelAttribute(model, DOMAIN_NAME,
                () -> DomainType.DEFAULT_DOMAIN_NAME);
    }

    private static String getDomainVersion(MetaModelType model) {
        return getMetaModelAttribute(model, DOMAIN_VERSION,
                () -> DomainType.DEFAULT_DOMAIN_VERSION);
    }

    private static String getTypeName(MetaModelType model) {
        return getOptionalMetaModelAttribute(model,
                DOMAIN_TYPE_NAME, () -> getDefaultTypeName(model));
    }

    private static String getMetaModelAttribute(
            MetaModelType model, String attributeName,
            Supplier<String> defaultValue) {
        return getMetaModelAttribtue(model, attributeName,
                Objects::nonNull, defaultValue);
    }

    private static String getOptionalMetaModelAttribute(
            MetaModelType model, String attributeName,
            Supplier<String> defaultValue) {
        return getMetaModelAttribtue(model, attributeName,
                DomainMetaModel::isNullOrEmpty,
                defaultValue);
    }

    private static boolean isNullOrEmpty(String s) {
        return Objects.isNull(s) || s.isEmpty();
    }

    private static String getDefaultTypeName(MetaModelType model) {
        String[] parts = PATTERN_DOT_SPLITTER.split(model.getTypeName());
        return parts[parts.length - 1];
    }

    private static String getMetaModelAttribtue(MetaModelType model,
            String attributeName,
            Predicate<String> attributeCheck,
            Supplier<String> defaultValue) {
        Map<String, String> attributes = model.getMetaModelAttributes();
        return (attributeCheck.test(attributeName)
                ? attributes.get(attributeName)
                : defaultValue.get());
    }

//    private static <T> DataCodec<T> getDefaultCodec(Class<T> domainTypeClass) {
//        
//    }
//    
//    private static DomainEntity toEntity (ModelType model) {
//        
//    }
//    
//    private static DomainType toType (ModelType model) {
//        
//    }
//    
//    private static DomainEvent toEvent (ModelType model) {
//        
//    }
//    
//    private static DomainOperation toEvent(ModelMethod model) {
//        
//    }
}
