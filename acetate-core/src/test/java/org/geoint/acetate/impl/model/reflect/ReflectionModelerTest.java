package org.geoint.acetate.impl.model.reflect;

import java.util.Collection;
import org.geoint.acetate.domain.model.ObjectModel;
import org.geoint.acetate.impl.domain.HardCodedDomainProvider;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 */
public class ReflectionModelerTest {

    /**
     * Test the reflection modeler returns the expected object models when
     * provided with a collection of classes.
     */
    @Test
    public void testClassReflection() throws Exception {
        Collection<ObjectModel> objectModels
                = ReflectionModeler.model(HardCodedDomainProvider.ACETATE_DOMAIN_CLASSES);

        assertEquals(HardCodedDomainProvider.ACETATE_DOMAIN_CLASSES.length,
                objectModels.size());

    }
}
