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

import org.geoint.acetate.serialization.MockValueBinaryCodec;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author steve_siebert
 */
public class DomainBuilderTest {

    private static final String NS = "org.geoint.acetate.test";
    private static final String V = "1.0";

    @Test
    public void testDefineValue() throws Exception {
        final String valueName = "testValue";
        final String valueDescription = "my test value";
        DomainModel m = new DomainBuilder(NS, V)
                .defineValue(valueName, valueDescription)
                .withDefaultBinCodec(MockValueBinaryCodec.class)
                .withDefaultCharCodec(MockValueBinaryCodec.class)
                .build()
                .build();

        assertEquals(NS, m.getNamespace());
        assertEquals(V, m.getVersion());
        assertTrue(m.getEvents().isEmpty());
        assertTrue(m.getResources().isEmpty());
        assertEquals(1, m.getValues().size());

        ValueType v = m.getValues().iterator().next();
        assertEquals(NS, v.getNamespace());
        assertEquals(V, v.getVersion());
        assertEquals(valueName, v.getName());
        assertTrue(v.getDescription().isPresent());
        assertEquals(valueDescription, v.getDescription().get());
        assertNotNull(v.getDefaultBinaryCodec());
        assertNotNull(v.getDefaultCharacterCodec());
    }

    
}
