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

import java.util.Collection;
import java.util.Optional;
import org.geoint.acetate.model.EventType;

/**
 * Instance of a domain event.
 * <p>
 * An event, most often, originates from a ResourceOperation and encapsulates
 * the behavior/changes that occurred on the domain at its current state. While
 * events open provide details of state change on a domain, this is not a
 * requirement (in fact, some of the most interesting events that occur in a
 * domain do not change the state of the current domain).
 * <p>
 * While a domain resource defines the "things" in the domain, as well as their
 * behavior, the domain event is arguable to "core" of a domain. Not only is the
 * state of the domain (its resources) sourced from events, domain events may
 * trigger additional events both within and outside of the domain. In other
 * words, while a domain resource define how to directly interact with the
 * domain (its API), domain events allows developers to lift the facade of the
 * domain and observe the context (the meaning) of what is occurring with a
 * domain. How specifically events are defined by a domain is implementation
 * specific; developers are encouraged to model a domain "event-first" based on
 * how a domain actually operates, and build a domain facade based on those
 * events.
 *
 * @author steve_siebert
 */
public interface EventInstance extends TypeInstance<EventType> {

    /**
     * Type instances that compose the event.
     *
     * @return event composite types
     */
    Collection<TypeInstanceRef> getComposites();

    /**
     * Retrieve event composite by name.
     *
     * @param compositeName composite name
     * @return composite or null
     */
    Optional<TypeInstanceRef> findComposite(String compositeName);
}
