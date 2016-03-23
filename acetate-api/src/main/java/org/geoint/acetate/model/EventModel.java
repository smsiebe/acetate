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
import java.util.Set;

/**
 * Domain-defined event describing the results of a successful
 * {@link ResourceOperation operation} execution.
 * <p>
 * Domains must be modeled appropriately to ensure that any changes to domain
 * state as well as any activities on the domain that would be of interest to
 * the domain, are modeled as events, and those events are created by successful
 * execution of {@link ResourceOperation operations}. How events are used, or
 * any transitive events that may occur as a result of an event beyond the scope
 * of the domain, is an application specific concern, and not one of the domain.
 *
 * @author steve_siebert
 */
public interface EventModel extends TypeModel {

    Set<? extends NamedTypeRef<? extends ValueModel>> getAttributes()
            throws InvalidModelException;

    Optional<? extends NamedTypeRef<? extends ValueModel>> findAttribute(String name)
            throws InvalidModelException;

}
