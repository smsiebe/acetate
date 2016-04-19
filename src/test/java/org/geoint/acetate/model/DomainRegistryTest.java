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
import static org.geoint.acetate.model.TestDomainUtil.*;
import org.geoint.acetate.model.design.DomainBuilder;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author steve_siebert
 */
public class DomainRegistryTest {

    public DomainRegistryTest() {
    }

    @Test
    public void testRegisteryBuilder() throws Exception {
        DomainRegistry reg = new DomainRegistry(); //empty registry
        DomainBuilder db = reg.builder(NAMESPACE, VERSION);
        addTestValue(db);
        Set<DomainType> types = db.createTypes();
        assertFalse(types.isEmpty());

        DomainType valueType = reg.resolve(new TypeDescriptor(NAMESPACE, VERSION, VALUE_TYPE_NAME));
        assertNotNull(valueType);
    }

    /**
     * Test that subsequent builders benefit from the types created from 
     * previous builders.
     * 
     * @throws Exception 
     */
    @Test
    public void testRegistryBuilderTwoBuilds() throws Exception {
        DomainRegistry reg = new DomainRegistry(); //empty registry
        DomainBuilder db = reg.builder(NAMESPACE, VERSION);
        addTestValue(db);
        Set<DomainType> types = db.createTypes();

        db = reg.builder(NAMESPACE, VERSION);
        addTestEvent(db);
        types = db.createTypes();
        assertFalse(types.isEmpty());

        DomainType valueType = reg.resolve(new TypeDescriptor(NAMESPACE, VERSION, VALUE_TYPE_NAME));
        assertNotNull(valueType);
        EventType eventType = (EventType) reg.resolve(new TypeDescriptor(NAMESPACE, VERSION, EVENT_TYPE_NAME));
        assertNotNull(eventType);
    }

}
