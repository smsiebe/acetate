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

import java.util.Set;
import java.util.stream.Stream;

/**
 * A unique model of a domain, as defined by its namespace and version.
 *
 * @author steve_siebert
 */
public interface DomainModel {

    /**
     * Namespace of the domain.
     *
     * @return domain namespace
     */
    String getNamespace();

    /**
     * Domain version.
     *
     * @return domain version
     */
    String getVersion();

    /**
     * Human-readable name(s) of the domain model.
     * <p>
     * These names need not be globally unique and may not be used as a means to
     * assert domain model identity, and may be thought of as aliases for the
     * domain.
     *
     * @return human readable name of the domain model
     */
    String[] getNames();

    /**
     * Models of domains this model depends upon.
     *
     * @return domain dependencies
     */
    Set<DomainModel> getModelDependencies();

    /**
     * Optional domain description.
     *
     * @return optional domain description
     */
    String getDescription();

    /**
     * Resources native to this domain.
     *
     * @return domain resource models
     */
    Set<ResourceModel> getNativeResources();

    /**
     * Values native to this domain.
     *
     * @return domain value models
     */
    Set<ValueModel> getNativeValues();

    /**
     * Events native to this domain.
     *
     * @return domain event models
     */
    Set<EventModel> getNativeEvents();
    
    /**
     * Return a stream of types native to this model.
     * 
     * @return native type stream
     */
    Stream<TypeModel> streamTypes();
}
