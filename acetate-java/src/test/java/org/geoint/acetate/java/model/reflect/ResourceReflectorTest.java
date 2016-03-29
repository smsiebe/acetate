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
package org.geoint.acetate.java.model.reflect;

/**
 * Test resource model generation through reflection.
 *
 * @author steve_siebert
 */
public class ResourceReflectorTest {

//    /**
//     * Test the a java class representing a domain resource is processed
//     * correctly with reflection.
//     *
//     * @throws Exception
//     */
//    @Test
//    public void testResourceClassReflection() throws Exception {
//        ResourceModel<MockAnnotatedResource> resource
//                = DomainClassReflector.model(MockAnnotatedResource.class);
//        assertEquals(MockAnnotatedResource.MOCK_NAMESPACE, resource.getNamespace());
//        assertEquals(MockAnnotatedResource.RESOURCE_TYPE, resource.getResourceType());
//        assertEquals(MockAnnotatedResource.MOCK_VERSION, resource.getResourceVersion());
//        assertEquals(MockAnnotatedResource.class, resource.getResourceClass());
//
//        assertEquals(1, resource.getOperations().size());
//
//        Optional<OperationModel> op = resource.findOperation(MockAnnotatedResource.OPERATION_NAME);
//        assertTrue(op.isPresent());
//        OperationModel o = op.get();
//        assertEquals(MockAnnotatedResource.MOCK_NAMESPACE, o.getNamespace());
//        assertEquals(MockAnnotatedResource.RESOURCE_TYPE, o.getResourceType());
//        assertEquals(MockAnnotatedResource.MOCK_VERSION, o.getResourceVersion());
//        assertEquals(MockAnnotatedResource.OPERATION_NAME, o.getName());
//        assertEquals(MockAnnotatedResource.class, o.getResourceClass());
//
//    }
//
//    @Test(expected = NotDomainResourceException.class)
//    public void testNoResourceClassException() throws Exception {
//        DomainClassReflector.model(String.class);
//    }
//
//    @Test
//    public void testResourceOperationMethodReflection() throws Exception {
//        Method m = MockAnnotatedResource.class.getMethod("operation");
//        OperationModel o = DomainClassReflector.forMethod(m);
//
//        assertEquals(MockAnnotatedResource.MOCK_NAMESPACE, o.getNamespace());
//        assertEquals(MockAnnotatedResource.RESOURCE_TYPE, o.getResourceType());
//        assertEquals(MockAnnotatedResource.MOCK_VERSION, o.getResourceVersion());
//        assertEquals(MockAnnotatedResource.OPERATION_NAME, o.getName());
//        assertEquals(MockAnnotatedResource.class, o.getResourceClass());
//    }
//
//    @Test(expected = NotDomainResourceException.class)
//    public void testNoResourceMethodException() throws Exception {
//        Method m = MockAnnotatedResource.class.getMethod("notOperation");
//        DomainClassReflector.forMethod(m);
//    }
}
