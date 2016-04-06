package org.geoint.acetate.model;

import org.geoint.acetate.ValueInstance;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.geoint.acetate.model.TestDomainUtil.*;

public class TypeDescriptorTest {

    @Test
    public void testValueOf() {
        ValueType type = new ValueType(NAMESPACE, VERSION, VALUE_TYPE_NAME);
        TypeDescriptor desc = TypeDescriptor.valueOf(type);

        assertEquals(NAMESPACE, desc.getNamespace());
        assertEquals(VERSION, desc.getVersion());
        assertEquals(VALUE_TYPE_NAME, desc.getType());
    }

    @Test
    public void testDescribesValues() {
        TypeDescriptor desc = new TypeDescriptor(NAMESPACE, VERSION, VALUE_TYPE_NAME);;
        assertTrue(desc.describes(NAMESPACE, VERSION, VALUE_TYPE_NAME));
    }

    @Test
    public void testDescribesType() {
        ValueType type = new ValueType(NAMESPACE, VERSION, VALUE_TYPE_NAME);
        TypeDescriptor desc = new TypeDescriptor(NAMESPACE, VERSION, VALUE_TYPE_NAME);

        assertTrue(desc.describes(type));
    }

    @Test
    public void testDescribesInstance() {
        TypeDescriptor desc = new TypeDescriptor(NAMESPACE, VERSION, VALUE_TYPE_NAME);
        ValueType type = new ValueType(NAMESPACE, VERSION, VALUE_TYPE_NAME);
        ValueInstance instance = type.createInstance(new byte[0]);

        assertTrue(desc.describes(instance));
    }

}
