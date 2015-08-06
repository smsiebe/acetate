/*
 * Copyright 2015 geoint.org.
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
package org.geoint.acetate.util;

import org.geoint.acetate.domain.DomainEntity;
import org.geoint.acetate.domain.DomainEvent;
import org.geoint.acetate.domain.DomainType;

/**
 * Utility methods useful when working with a domain model.
 *
 * @author steve_siebert
 */
public class DomainUtil {

    public static boolean isEntity(DomainType t) {
        return DomainEntity.class.isAssignableFrom(t.getClass());
    }

    public static boolean isEvent(DomainType t) {
        return DomainEvent.class.isAssignableFrom(t.getClass());
    }

}
