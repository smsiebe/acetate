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
//import org.geoint.acetate.serialization.MockValueBinaryCodec;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author steve_siebert
 */
public class DomainBuilderTest {

    private static final String NS = "org.geoint.acetate.test";
    private static final String V = "1.0";
    private static final String valueName = "testValue";
    private static final String valueDescription = "my test value";
    private static final String eventName = "testEvent";
    private static final String eventDesc = "my test desc";
    private static final String valueRefName = "eventValue";
    private static final String valueRefDesc = "event value";
    private static final String resourceName = "testResource";
    private static final String resourceDesc = "super cool resource";
    private static final String mapRefName = "mapRef";
    private static final String eventRefName = "myEventRef";

    @Test
    public void testDefineModel() throws Exception {
        DomainModel m = newTestDomainBuilder()
                .build();
        assertEquals(NS, m.getNamespace());
        assertEquals(V, m.getVersion());
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
        assertEquals(NS, v.getNamespace());
        assertEquals(V, v.getVersion());
        assertEquals(valueName, v.getName());
        assertTrue(v.getDescription().isPresent());
        assertEquals(valueDescription, v.getDescription().get());
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
        assertEquals(NS, e.getNamespace());
        assertEquals(V, e.getVersion());
        assertEquals(eventName, e.getName());
        assertTrue(e.getDescription().isPresent());
        assertEquals(eventDesc, e.getDescription().get());
        assertEquals(1, e.getComposites().size());
        assertTrue(e.findComposite(valueRefName).isPresent());

        //test event composite
        NamedRef vRef = e.findComposite(valueRefName).get();
        assertTrue(vRef.getDescription().isPresent());
        assertEquals(valueRefDesc, vRef.getDescription().get());
        assertEquals(valueRefName, vRef.getName());
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
        assertEquals(NS, r.getNamespace());
        assertEquals(V, r.getVersion());
        assertEquals(resourceName, r.getName());
        assertTrue(r.getDescription().isPresent());
        assertEquals(resourceDesc, r.getDescription().get());
        assertEquals(1, r.getComposites().size());
        assertTrue(r.findComposite(valueRefName).isPresent());

        //test value composite
        NamedRef vRef = r.findComposite(valueRefName).get();
        assertTrue(vRef.getDescription().isPresent());
        assertEquals(valueRefDesc, vRef.getDescription().get());
        assertEquals(valueRefName, vRef.getName());
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
        DomainBuilder b = new DomainBuilder(NS, V);
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
        DomainBuilder b = new DomainBuilder(NS, V);
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
        DomainBuilder b = new DomainBuilder(NS, V);
        addTestEvent(b);
        /* addTestValue(b); <-- no value added to descriptor, do not uncomment or you invalidate test */

        DomainModel m = b.build(); //InvalidModelException expected
    }

    @Test
    public void testMapReference() throws InvalidModelException {
        DomainBuilder b = new DomainBuilder(NS, V);
        addTestValue(b);
        addTestEvent(b);
        b.defineResource("resourceWithMap")
                .withCompositeMap(mapRefName)
                .keyType(valueRefName, valueName)
                .withDescription(valueRefDesc)
                .build() //map key ref
                .valueRef(eventRefName)
                .referencedType(eventName)
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
        Optional<NamedRef> cRef = r.findComposite(mapRefName);
        assertTrue(cRef.isPresent());
        NamedRef ref = cRef.get();
        assertTrue(ref instanceof NamedMapRef);
        NamedMapRef mapRef = (NamedMapRef) ref;

        //test key
        assertEquals(valueRefName, mapRef.getKeyRef().getName());
        assertTrue(mapRef.getKeyRef().getDescription().isPresent());
        assertEquals(valueRefDesc, mapRef.getKeyRef().getDescription().get());
        assertFalse(mapRef.getKeyRef().isCollection());
        DomainType keyType = mapRef.getKeyRef().getReferencedType();
        assertTrue(keyType instanceof ValueType);
        ValueType keyValueType = (ValueType) keyType;
        assertEquals(valueName, keyValueType.getName());

        //test value
        NamedRef valueRef = mapRef.getValueRef();
        assertEquals(eventRefName, valueRef.getName());
        assertTrue(valueRef instanceof NamedTypeRef);
        NamedTypeRef valueTypeRef = (NamedTypeRef) valueRef;
        DomainType valueType = valueTypeRef.getReferencedType();
        assertTrue(valueType instanceof EventType);
        EventType valueEventType = (EventType) valueType;
        assertEquals(eventName, valueEventType.getName());
    }

    private DomainBuilder addTestResource(DomainBuilder b)
            throws InvalidModelException {
        return b.defineResource(resourceName, resourceDesc)
                .withCompositeType(valueRefName, valueName)
                .withDescription(valueRefDesc)
                .build() //ref
                .build(); //resource
    }

    private DomainBuilder addTestEvent(DomainBuilder b)
            throws InvalidModelException {
        b.defineEvent(eventName, eventDesc)
                .withCompositeType(valueRefName, valueName)
                .withDescription(valueRefDesc)
                .build() //ref
                .build(); //event
        return b;
    }

    private DomainBuilder addTestValue(DomainBuilder b)
            throws InvalidModelException {
        b.defineValue(valueName)
                //                .withDefaultBinCodec(MockValueBinaryCodec.class)
                //                .withDefaultCharCodec(MockValueBinaryCodec.class)
                .withDescription(valueDescription)
                .build();
        return b;
    }

    private DomainBuilder newTestDomainBuilder() {
        return new DomainBuilder(NS, V);
    }
}
