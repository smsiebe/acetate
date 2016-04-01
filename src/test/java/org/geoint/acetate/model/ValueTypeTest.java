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
public class ValueTypeTest {

    private static final String NS = "org.geoint.acetate.test";
    private static final String TN = "mockValue";
    private static final String V = "1.0";

    /**
     * Test that the default char codec cannot be null.
     *
     * @throws Exception
     */
    @Test(expected = InvalidModelException.class)
    public void testNullDefaultCharCodec() throws Exception {

        final ValueType value
                = new ValueType(NS, V, TN, null, new MockValueBinaryCodec());//InvalidModelException expected
    }

    /**
     * Test that the default binary codec cannot be null.
     *
     * @throws Exception
     */
    @Test(expected = InvalidModelException.class)
    public void testNullDefaultBinCodec() throws Exception {
        final ValueType value
                = new ValueType(NS, V, TN, new MockValueBinaryCodec(), null);//InvalidModelException expected
    }
}
