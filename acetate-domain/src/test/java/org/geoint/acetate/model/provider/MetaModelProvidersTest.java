package org.geoint.acetate.model.provider;

import java.util.Collection;
import java.util.Optional;
import org.geoint.acetate.domain.model.DataModel;
import org.geoint.acetate.impl.domain.model.MetaVersionImpl;
import org.geoint.acetate.domain.provider.ModelProvider;
import static org.junit.Assert.*;
import org.junit.Test;

public class MetaModelProvidersTest {

    /**
     * Test MetaProviders are discovered/loaded with ServiceLoader.
     */
    @Test
    public void testLoadProvidersFromServiceLoader() {
        Collection<ModelProvider> providers = MetaModelProviders.getProviders();
        assertTrue(!providers.isEmpty());
        assertTrue(providers.stream()
                .filter((p) -> p.getClass().equals(HardCodedDomainProvider.class))
                .findAny()
                .isPresent());
    }

    /**
     * Test that checking for an unknown domain model returns a null-containing
     * Optional.
     */
    @Test
    public void testFindUnknownDomain() {
        assertTrue(!MetaModelProviders
                .getDomain("unknown", MetaVersionImpl.DEFAULT_VERSION)
                .isPresent()
        );
    }

    /**
     * Test that the expected domain model is returned for a known model.
     */
    @Test
    public void testKnownDomain() {
        Optional<DataModel> acetate = MetaModelProviders.getDomain(DataModel.ACETATE_DOMAIN_NAME,
                MetaVersionImpl.valueOf(DataModel.ACETATE_DOMAIN_VERSION)
        );
        assertTrue("Acetate domain model was not loaded", acetate.isPresent());
        assertEquals(HardCodedDomainProvider.ACETATE_DOMAIN_CLASSES.length,
                acetate.get().findAll().size());
    }
}
