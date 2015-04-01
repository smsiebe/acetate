package org.geoint.acetate.bound;

import java.util.Collection;
import org.geoint.acetate.model.DataConstraintException;
import org.geoint.acetate.model.DataModel;
import org.geoint.acetate.model.ModelConstraintException;

/**
 * Data bound to a {@link DataModel}.
 */
public interface BoundData {

    /**
     * Return the data component(s) mapped to the specified path.
     *
     * @param path data model path
     * @return data component(s) mapped to this model path
     */
    Collection<BoundField> get(String path);

    /**
     * Model field names that have had data mapped to it.
     *
     * @return collection of <i>.</i> delimited model paths to fields with data
     */
    Collection<String> getFieldPaths();

    /**
     * The model with which this data is bound.
     *
     * @return model
     */
    DataModel getModel();

    /**
     * Validate the bound data against all data model constraints.
     *
     * @throws ModelConstraintException thrown if there are any conflicts with
     * the data model
     */
    void validateModel() throws ModelConstraintException;

    /**
     * Validate the bound data against all data constraints as defined in the
     * FieldModel components.
     *
     * @throws DataConstraintException thrown if there are any conflicts with
     * any applicable FieldModel constraint
     */
    void validateData() throws DataConstraintException;

}
