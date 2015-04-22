package org.geoint.acetate.structure;

import java.util.Optional;
import org.geoint.acetate.model.ValueModel;

/**
 * An data structure instance using components of a {@link DataModel} as
 * components of the structure.
 *
 * @param <T> corresponding Java type for this data
 * @see DataModel
 */
public interface DataStructure<T> {

    /**
     * Return the meta model for this data models GUID field.
     *
     * @return model for the GUID field
     */
    ValueModel<?> getGUID();

    /**
     * Return the component model for the data models version field.
     *
     * @return version model for the data model
     */
    Optional<ValueModel<?>> getVersion();

    /**
     * Visit the data structure in the sequence the actual data structure is
     * sequenced.
     *
     * @param visitor
     */
    void visit(DataStructureVisitor visitor);
}
