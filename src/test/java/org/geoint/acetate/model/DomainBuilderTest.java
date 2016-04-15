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

import org.geoint.acetate.model.design.DomainBuilder;
import java.util.Optional;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.geoint.acetate.model.TestDomainUtil.*;

/**
 *
 * @author steve_siebert
 */
public class DomainBuilderTest {

    @Test
    public void testDefineModel() throws Exception {
        DomainModel m = newTestDomainBuilder()
                .build();
        assertEquals(NAMESPACE, m.getNamespace());
        assertEquals(VERSION, m.getVersion());
        assertTrue(m.getEvents().isEmpty());
        assertTrue(m.getResources().isEmpty());
        assertTrue(m.getValues().isEmpty());
    }

    @Test
    public void testDefineValue() throws Exception {
        DomainModel m = addTestValue(newTestDomainBuilder()).build();
        assertEquals(0, m.getResources().size());
        assertEquals(0, m.getEvents().size());
        assertEquals(1, m.getValues().size());

        ValueType v = m.getValues().iterator().next();
        assertEquals(NAMESPACE, v.getNamespace());
        assertEquals(VERSION, v.getVersion());
        assertEquals(VALUE_TYPE_NAME, v.getName());
        assertTrue(v.getDescription().isPresent());
        assertEquals(VALUE_DESC, v.getDescription().get());
//        assertNotNull(v.getDefaultBinaryCodec());
//        assertNotNull(v.getDefaultCharacterCodec());
    }

    @Test
    public void testDefineEvent() throws Exception {
        DomainModel m = addTestEvent(addTestValue(newTestDomainBuilder())).build();
        assertEquals(0, m.getResources().size());
        assertEquals(1, m.getValues().size());
        assertEquals(1, m.getEvents().size());

        //test event
        EventType e = m.getEvents().iterator().next();
        assertEquals(NAMESPACE, e.getNamespace());
        assertEquals(VERSION, e.getVersion());
        assertEquals(EVENT_TYPE_NAME, e.getName());
        assertTrue(e.getDescription().isPresent());
        assertEquals(EVENT_DESC, e.getDescription().get());
        assertEquals(1, e.getComposites().size());
        assertTrue(e.findComposite(VALUE_REF_NAME).isPresent());

        //test event composite
        NamedRef vRef = e.findComposite(VALUE_REF_NAME).get();
        assertTrue(vRef.getDescription().isPresent());
        assertEquals(VALUE_REF_DESC, vRef.getDescription().get());
        assertEquals(VALUE_REF_NAME, vRef.getName());
        assertTrue(vRef instanceof NamedTypeRef);
        NamedTypeRef typeRef = (NamedTypeRef) vRef;
        ValueType vType = m.getValues().iterator().next();
        assertEquals(vType, typeRef.getReferencedType());

    }

    @Test
    public void testDefineResource() throws Exception {
        DomainModel m = addTestResource(
                addTestEvent(
                        addTestValue(
                                newTestDomainBuilder()))
        ).build();

        assertEquals(1, m.getValues().size());
        assertEquals(1, m.getEvents().size());
        assertEquals(1, m.getResources().size());

        //test resource
        ResourceType r = m.getResources().iterator().next();
        assertEquals(NAMESPACE, r.getNamespace());
        assertEquals(VERSION, r.getVersion());
        assertEquals(RESOURCE_TYPE_NAME, r.getName());
        assertTrue(r.getDescription().isPresent());
        assertEquals(RESOURCE_DESC, r.getDescription().get());
        assertEquals(1, r.getComposites().size());
        assertTrue(r.findComposite(VALUE_REF_NAME).isPresent());

        //test value composite
        NamedRef vRef = r.findComposite(VALUE_REF_NAME).get();
        assertTrue(vRef.getDescription().isPresent());
        assertEquals(VALUE_REF_DESC, vRef.getDescription().get());
        assertEquals(VALUE_REF_NAME, vRef.getName());
        assertTrue(vRef instanceof NamedTypeRef);
        NamedTypeRef typeRef = (NamedTypeRef) vRef;
        ValueType vType = m.getValues().iterator().next();
        assertEquals(vType, typeRef.getReferencedType());
    }

    /**
     * Test defining a domain model containing an EventType and ValueType, but
     * defined out of dependency sequence.
     * <p>
     * What we are testing here is not that the builder constructs each type
     * correctly, but that the types are resolved even when they are defined
     * "chaotically".
     *
     * @throws Exception
     */
    @Test
    public void testDefineEventOutOfDependencyOrder() throws Exception {
        DomainBuilder b = new DomainBuilder(NAMESPACE, VERSION);
        addTestEvent(b); //adding event descriptor before value it depends on
        addTestValue(b); //then adding the dependecy

        DomainModel m = b.build();

        assertEquals(0, m.getResources().size());
        assertEquals(1, m.getValues().size());
        assertEquals(1, m.getEvents().size());
    }

    /**
     * Test defining a domain model containing a Value, Event, and ResourceType,
     * but they are defined out of dependency order.
     * <p>
     * What we are testing here is not that the builder constructs each type
     * correctly, but that the types are resolved even when they are defined
     * "chaotically".
     *
     * @throws Exception
     */
    @Test
    public void testDefineResourceOutOfDependencyOrder() throws Exception {
        DomainBuilder b = new DomainBuilder(NAMESPACE, VERSION);
        addTestResource(b); //adding resource descriptor before event and value it depends on
        addTestEvent(b); //adding event descriptor before value it depends on
        addTestValue(b); //then adding the dependecy

        DomainModel m = b.build();

        assertEquals(1, m.getValues().size());
        assertEquals(1, m.getEvents().size());
        assertEquals(1, m.getResources().size());
    }

    /**
     * Test that the builder fails to construct a DomainModel, and throws and
     * InvalidModelException, when a required type is missing.
     *
     * @throws InvalidModelException
     */
    @Test(expected = InvalidModelException.class)
    public void testInvalidMissingValue() throws InvalidModelException {
        DomainBuilder b = new DomainBuilder(NAMESPACE, VERSION);
        addTestEvent(b);
        /* addTestValue(b); <-- no value added to descriptor, do not uncomment or you invalidate test */

        DomainModel m = b.build(); //InvalidModelException expected
    }

    @Test
    public void testMapReference() throws InvalidModelException {
        DomainBuilder b = new DomainBuilder(NAMESPACE, VERSION);
        addTestValue(b);
        addTestEvent(b);
        b.defineResource("resourceWithMap")
                .withCompositeMap(MAP_REF_NAME)
                .keyType(VALUE_REF_NAME, VALUE_TYPE_NAME)
                .withDescription(VALUE_REF_DESC)
                .build() //map key ref
                .valueType(EVENT_REF_NAME, EVENT_TYPE_NAME)
                .isCollection(true)
                .build() //may key value
                .build() //resource
                .build(); //domain

        DomainModel m = b.build();

        assertEquals(1, m.getValues().size());
        assertEquals(1, m.getEvents().size());
        assertEquals(1, m.getResources().size());

        ResourceType r = m.getResources().iterator().next();
        assertEquals(1, r.getComposites().size());
        Optional<NamedRef> cRef = r.findComposite(MAP_REF_NAME);
        assertTrue(cRef.isPresent());
        NamedRef ref = cRef.get();
        assertTrue(ref instanceof NamedMapRef);
        NamedMapRef mapRef = (NamedMapRef) ref;

        //test key
        assertEquals(VALUE_REF_NAME, mapRef.getKeyRef().getName());
        assertTrue(mapRef.getKeyRef().getDescription().isPresent());
        assertEquals(VALUE_REF_DESC, mapRef.getKeyRef().getDescription().get());
        assertFalse(mapRef.getKeyRef().isCollection());
        DomainType keyType = mapRef.getKeyRef().getReferencedType();
        assertTrue(keyType instanceof ValueType);
        ValueType keyValueType = (ValueType) keyType;
        assertEquals(VALUE_TYPE_NAME, keyValueType.getName());

        //test value
        NamedRef valueRef = mapRef.getValueRef();
        assertEquals(EVENT_REF_NAME, valueRef.getName());
        assertTrue(valueRef instanceof NamedTypeRef);
        NamedTypeRef valueTypeRef = (NamedTypeRef) valueRef;
        DomainType valueType = valueTypeRef.getReferencedType();
        assertTrue(valueType instanceof EventType);
        EventType valueEventType = (EventType) valueType;
        assertEquals(EVENT_TYPE_NAME, valueEventType.getName());
    }

}
