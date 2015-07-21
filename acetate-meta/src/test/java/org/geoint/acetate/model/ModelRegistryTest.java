package org.geoint.acetate.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class ModelRegistryTest {

    /**
     * Ensure that the result of calling RegistryModel#getAnnotated with a model
     * annotated annotated with {@link Inherited} will include subclasses not
     * locally declared with the model annotation (it actually inherits).
     *
     */
    @Test
    public void testGetAnnotatedWithInheritedAnnotation() throws Exception {

    }

    /**
     * Ensure the result of calling RegistryModel#getAnnotated with a model
     * annotation without the {@link Inherited inherited} annotation will not
     * include any subclasses not locally declared with the model annotation.
     */
    @Test
    public void testGetAnnotedWithNonInheritedAnnotation() throws Exception {

    }

}
