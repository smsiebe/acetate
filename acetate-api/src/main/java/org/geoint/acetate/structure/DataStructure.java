package org.geoint.acetate.structure;

import org.geoint.acetate.data.Data;
import org.geoint.acetate.model.DataModel;

/**
 * The specific usage of components within a {@link DataModel} defines the
 * structure of data.
 *
 * @see DataModel
 * @see Data
 */
public interface DataStructure {

    /**
     * Returns the backing data model for the structure.
     *
     * @return generic data model of which the structure is based
     */
    DataModel getModel();

    /**
     * Visit the data structure in the sequence the actual data structure is
     * sequenced.
     *
     * @param visitor
     */
    void visit(DataStructureVisitor visitor);
}
