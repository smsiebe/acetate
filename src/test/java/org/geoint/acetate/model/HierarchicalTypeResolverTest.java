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

import org.geoint.acetate.model.resolve.MapTypeResolver;
import org.geoint.acetate.model.resolve.HierarchicalTypeResolver;
import java.util.Collections;
import java.util.Optional;
import org.junit.Test;
import static org.junit.Assert.*;
import org.geoint.acetate.model.resolve.DomainTypeResolver;
import org.geoint.acetate.model.resolve.UnresolvedException;

/**
 *
 * @author steve_siebert
 */
public class HierarchicalTypeResolverTest {

    private static final String NS = "testNamespace";
    private static final String V = "1.0";

    public HierarchicalTypeResolverTest() {
    }

    @Test
    public void testHierarchicalResolve() throws Exception {
        final String rootEventType = "root";
        final String tierOneEventType = "tier1";
        final String tierTwoEventTime = "tier2";
        TrackingTypeResolver rootResolver = eventResolver(NS, V, rootEventType);
        TrackingTypeResolver tierOneResolver = eventResolver(NS, V, tierOneEventType);
        TrackingTypeResolver tierTwoResolver = eventResolver(NS, V, tierTwoEventTime);

        HierarchicalTypeResolver<TypeDescriptor> resolver
                = HierarchicalTypeResolver.newHierarchy(rootResolver)
                .addChild(tierOneResolver)
                .addChild(tierTwoResolver);

        //resolve from root
        DomainType type = resolver.resolve(new TypeDescriptor(NS, V, rootEventType));
        assertTrue(type.isType(NS, V, rootEventType));
        assertEquals(1, rootResolver.numResolved);
        assertEquals(0, tierOneResolver.numResolved);
        assertEquals(0, tierTwoResolver.numResolved);

        //resolve from tier1
        type = resolver.resolve(new TypeDescriptor(NS, V, tierOneEventType));
        assertTrue(type.isType(NS, V, tierOneEventType));
        assertEquals(1, rootResolver.numResolved);
        assertEquals(1, tierOneResolver.numResolved);
        assertEquals(0, tierTwoResolver.numResolved);

        //resolve from tier2
        type = resolver.resolve(new TypeDescriptor(NS, V, tierTwoEventTime));
        assertTrue(type.isType(NS, V, tierTwoEventTime));
        assertEquals(1, rootResolver.numResolved);
        assertEquals(1, tierOneResolver.numResolved);
        assertEquals(1, tierTwoResolver.numResolved);
    }

    private TrackingTypeResolver eventResolver(String namespace,
            String version, String typeName) throws InvalidModelException {
        final TypeDescriptor td = new TypeDescriptor(namespace, version, typeName);
        return new TrackingTypeResolver(
                new MapTypeResolver<>(DomainType::getTypeDescriptor,
                        new EventType(td, Collections.EMPTY_LIST)));
    }

    private class TrackingTypeResolver implements DomainTypeResolver<TypeDescriptor> {

        int numResolved;
        DomainTypeResolver<TypeDescriptor> resolver;

        public TrackingTypeResolver(DomainTypeResolver<TypeDescriptor> resolver) {
            this.resolver = resolver;
        }

        @Override
        public DomainType resolve(TypeDescriptor td) throws UnresolvedException {
            DomainType t = resolver.resolve(td);
            numResolved++;
            return t;
        }

    }
}
