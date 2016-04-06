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

/**
 * Utility class used to create mock test domain objects.
 */
public class TestDomainUtil {

    public static final String NAMESPACE = "org.geoint.acetate.test";
    public static final String VERSION = "1.0";
    public static final String VALUE_TYPE_NAME = "testValue";
    public static final String VALUE_DESC = "my test value";
    public static final String EVENT_TYPE_NAME = "testEvent";
    public static final String EVENT_DESC = "my test desc";
    public static final String VALUE_REF_NAME = "eventValue";
    public static final String VALUE_REF_DESC = "event value";
    public static final String RESOURCE_TYPE_NAME = "testResource";
    public static final String RESOURCE_DESC = "super cool resource";
    public static final String MAP_REF_NAME = "mapRef";
    public static final String EVENT_REF_NAME = "myEventRef";

    public static DomainBuilder addTestResource(DomainBuilder b)
            throws InvalidModelException {
        return b.defineResource(RESOURCE_TYPE_NAME, RESOURCE_DESC)
                .withCompositeType(VALUE_REF_NAME, VALUE_TYPE_NAME)
                .withDescription(VALUE_REF_DESC)
                .build() //ref
                .build(); //resource
    }

    public static DomainBuilder addTestEvent(DomainBuilder b)
            throws InvalidModelException {
        b.defineEvent(EVENT_TYPE_NAME, EVENT_DESC)
                .withCompositeType(VALUE_REF_NAME, VALUE_TYPE_NAME)
                .withDescription(VALUE_REF_DESC)
                .build() //ref
                .build(); //event
        return b;
    }

    public static DomainBuilder addTestValue(DomainBuilder b)
            throws InvalidModelException {
        b.defineValue(VALUE_TYPE_NAME)
                .withDescription(VALUE_DESC)
                .build();
        return b;
    }

    public static DomainBuilder newTestDomainBuilder() {
        return new DomainBuilder(NAMESPACE, VERSION);
    }
}
