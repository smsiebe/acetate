package org.geoint.acetate.spi.structure;

import org.geoint.acetate.model.DataModel;
import org.geoint.acetate.model.ModelException;
import org.geoint.acetate.structure.DataStructure;

/**
 * Provides a {@link DataStructure} based on the components of a
 * {@link DataModel}.
 */
@FunctionalInteface
public interface StructureProvider {

    public DataStructure createStructure(DataModel model) 
            throws ModelException, StructureException;
}
