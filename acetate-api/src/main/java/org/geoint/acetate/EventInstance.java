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
package org.geoint.acetate;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Set;
import org.geoint.acetate.model.EventModel;

/**
 * Instance of a domain event.
 *
 * @author steve_siebert
 * @param <M> model type used to describe the domain event
 */
public interface EventInstance<M extends EventModel> extends TypeInstance<M> {

    /**
     * Globally unique event identifier.
     *
     * @return event identifier
     */
    String getEventGuid();

    /**
     * Namespace of the related resource.
     *
     * @return resource namespace
     */
    String getResourceNamespace();

    /**
     * Domain type of related resource.
     *
     * @return resource type
     */
    String getResourceType();

    /**
     * Version of the related resource.
     *
     * @return resource version
     */
    String getResourceVerison();

    /**
     * Name of the operation which created this event.
     *
     * @return operation name
     */
    String getOperationName();

    /**
     * Unique identifier of the related resource instance.
     *
     * @return resource id
     */
    String getInstaceGuid();

    /**
     * Version of the resource instance <b>before</b> the operation was
     * executed.
     *
     * @return resource instance version
     */
    String getInstanceVersion();

    /**
     * Local start time of the machine which executed the operation.
     *
     * @return execution start time
     */
    ZonedDateTime getExecutionTime();

    /**
     * Duration of the operation execution.
     *
     * @return execution duration
     */
    Duration getExecutionDuration();

    /**
     * Attributes of the event.
     *
     * @return event attributes
     */
    Set<? extends InstanceRef<? extends ValueInstance>> getAttributes();

    /**
     * Retrieve event attribute by name.
     *
     * @param attributeName attribute name
     * @return attribute or null
     */
    Optional<? extends InstanceRef<? extends ValueInstance>> findAttribute(String attributeName);
}
