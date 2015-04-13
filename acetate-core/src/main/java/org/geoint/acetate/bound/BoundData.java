package org.geoint.acetate.bound;

import java.util.Collection;
import java.util.Optional;
import org.geoint.acetate.bind.DataBinder;
import org.geoint.acetate.bound.sparse.SparseField;
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
    Optional<String> getVersion();

    /**
     * Return the root bound data component.
     *
     * @return root bound data component
     */
    BoundComponent get();

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
    Optional<Collection<BoundComponent>> get(String path);

    /**
     * Returns data component(s) that were read by a {@link DataBinder} but were
     * not able to be mapped to a component in the {@link DataModel}.
     *
     * @return collection of sparse data fields
     */
    Collection<? extends SparseField> getSparse();

    /**
     * The model with which this data is bound.
     *
     * @return model
     */
    DataModel getModel();

    /**
     * Validates the bound data against the model and all data constraints.
     *
     * This method is functionally equivalent to:
     *
     * {@code
     *    BoundData bd = ...
     *    bd.validateModel();
     *    bd.validateData();
     * }
     *
     * @throws ModelConstraintException
     * @throws DataConstraintException
     */
    void validate() throws ModelConstraintException, DataConstraintException;

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
