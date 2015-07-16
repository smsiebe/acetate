package org.geoint.acetate.domain.provider.reflect;

import org.geoint.acetate.domain.provider.reflect.ReflectionModeler;
import java.util.Collection;
import java.util.Optional;
import org.geoint.acetate.domain.model.DataModel;
import org.geoint.acetate.domain.model.ObjectModel;
import org.geoint.acetate.domain.model.OperationModel;
import org.geoint.acetate.model.provider.HardCodedDomainProvider;
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
        Collection<DataModel> domainModels
                = ReflectionModeler.model(HardCodedDomainProvider.ACETATE_DOMAIN_CLASSES);

        //only should be the acetate domain model domain
        assertEquals(1, domainModels.size());

        DataModel acetateDomain = domainModels.iterator().next();

        //the domain should contain the expected number of object models
        assertEquals(HardCodedDomainProvider.ACETATE_DOMAIN_CLASSES.length,
                acetateDomain.findAll().size());

    }
    
    @Test
    public void testOperationReflection() throws Exception {
        Collection<DataModel> domainModels
                = ReflectionModeler.model(HardCodedDomainProvider.ACETATE_DOMAIN_CLASSES);
        //only should be the acetate domain model domain
        assertEquals(1, domainModels.size());

        DataModel acetateDomain = domainModels.iterator().next();
        Optional<ObjectModel> optional = acetateDomain.find("Object Model");
        assertTrue(optional.isPresent());
        
        ObjectModel objectModel = optional.get();
        
        Collection<OperationModel> operations = objectModel.getOperations();
        //assertEquals(9, operations.size());
        for (OperationModel opm : operations) {
            System.out.println("Found operation: "+opm.getOperationName());
        }
    }
}
