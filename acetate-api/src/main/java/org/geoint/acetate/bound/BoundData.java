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
     * The bound data unique identity.
     *
     * @return unique data identity
     */
    String getGUID();

    /**
     * The bound data version, if supported.
     *
     * @return bound data version, or null if not supported for this data type
     */
    String getVersion();

    /**
     * Return the data component(s) mapped to the specified path.
     * <p>
     * This method will return all components that were mapped to a specific
     * path. Depending on the path, the data may be sourced from multiple class
     * instances within the data mode.
     *
     * @param path data model path
     * @return data component(s) mapped to this model path
     */
    Collection<BoundField> get(String path);

    /**
     * Returns data component(s) that were read by a {@link DataBinder} but were
     * not able to be mapped to a component in the {@link DataModel}.
     *
     * @return collection of sparse data fields
     */
    Collection<BoundField> getSparse();

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
