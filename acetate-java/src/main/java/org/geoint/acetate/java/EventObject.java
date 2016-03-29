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
package org.geoint.acetate.java;

import org.geoint.acetate.java.model.EventClass;
import java.util.Optional;
import java.util.Set;
import org.geoint.acetate.EventInstance;

/**
 *
 * @author steve_siebert
 * @param <T>
 */
public interface EventObject<T>
        extends TypeObject<T, EventClass<T>>, EventInstance<EventClass<T>> {

    /**
     * Attributes of the event.
     *
     * @return event attributes
     */
    @Override
    Set<ValueObjectRef<?>> getAttributes();

    /**
     * Retrieve event attribute by name.
     *
     * @param attributeName attribute name
     * @return attribute or null
     */
    @Override
    Optional<ValueObjectRef<?>> findAttribute(String attributeName);
}