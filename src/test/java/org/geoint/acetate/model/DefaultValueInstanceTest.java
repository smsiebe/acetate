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

import org.geoint.acetate.model.design.DomainModelBuilder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.geoint.acetate.ValueInstance;
import static org.geoint.acetate.model.TestDomainUtil.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class DefaultValueInstanceTest {

    @Test
    public void test() throws Exception {
        final byte[] valueBytes = "ValueValue".getBytes(StandardCharsets.UTF_8);
        DomainModelBuilder b = newTestDomainBuilder();
        addTestValue(b);
        DomainModel m = b.build();

        ValueType type = m.getValues().iterator().next();
        ValueInstance instance = ValueInstance.newInstance(type, valueBytes);

        assertTrue(Objects.nonNull(instance));
        assertEquals(type, instance.getModel());
        assertTrue(instance.getTypeDescriptor().describes(type));
        assertArrayEquals(valueBytes, instance.asBytes());
    }
}
