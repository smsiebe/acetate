package org.geoint.acetate.bind;

import java.util.Collection;
import java.util.Map;
import org.geoint.acetate.metamodel.DataModel;

/**
 * The binder links the DataModel to a java object, providing access to the data
 * model.
 *
 * @param <F> the object type being bound
 */
public interface DataBinder<F> {

    /**
     * Retrieve the data object from the binder.
     *
     * @return the data object
     */
    F get();

    /**
     * The model used by the binder.
     *
     * @return data model
     */
    DataModel<F> getModel();

    /**
     * Any fields that were in the template but could not be matched back to the
     * data object.
     *
     * @return
     */
    Map<String, String> getUnmatched();

    /**
     * The fields that have been bound between the data model and the bound
     * object instance.
     *
     * @return fields that have been bound.
     */
    Collection<FieldBinder<F, ?, ?>> getFields();

}
