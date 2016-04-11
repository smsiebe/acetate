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

import org.geoint.acetate.model.resolve.MemoryTypeResolver;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

/**
 *
 * @author steve_siebert
 */
public class MemoryTypeResolverTest {

    private static final String NS = "testNamespace";
    private static final String V = "1.0";
    private static final String TN = "testEvent";
    private static EventType TEST_EVENT;

    @BeforeClass
    public static void beforeClass() throws InvalidModelException {
        TEST_EVENT = new EventType(NS, V, TN, Collections.EMPTY_LIST);
    }

    @Test
    public void testDefaultResolver() throws InvalidModelException {
        MemoryTypeResolver r = new MemoryTypeResolver();
        r.getTypes().add(TEST_EVENT);
        assertTrue(r.resolve(NS, V, TN).isPresent());
    }

    @Test
    public void testArrayBackedResolver() throws InvalidModelException {
        MemoryTypeResolver r = new MemoryTypeResolver(TEST_EVENT);
        assertTrue(r.resolve(NS, V, TN).isPresent());
    }

    @Test
    public void testProvidedCollectionResolver() throws InvalidModelException {
        Collection<DomainType> types = new ArrayList<>();
        MemoryTypeResolver r = new MemoryTypeResolver(types);
        types.add(TEST_EVENT);
        assertTrue(r.resolve(NS, V, TN).isPresent());
    }

}
