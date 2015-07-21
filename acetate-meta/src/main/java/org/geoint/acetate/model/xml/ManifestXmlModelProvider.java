package org.geoint.acetate.model.xml;

import java.util.Collection;
import java.util.Collections;
import org.geoint.acetate.model.ModelElement;
import org.geoint.acetate.model.provider.ModelProvider;

/**
 * Provides model elements described in {@link ModelXmlDescriptor xml descriptor
 * files} referenced in the MANIFEST.mf.
 */
public class ManifestXmlModelProvider implements ModelProvider {

    @Override
    public Collection<ModelElement> getModelElements() {
        return Collections.EMPTY_LIST;
    }

}
