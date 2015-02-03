package org.geoint.acetate.bind;

import java.util.Collection;
import org.geoint.acetate.metamodel.DataModel;

/**
 * The binder links the DataModel to a java object, providing access to the data
 * model.
 *
 * This object must be immutable.
 *
 * @param <D> the object type being bound
 */
public interface BoundData<D> {

    /**
     * Retrieve the structured model for this data.
     *
     * @return the data object
     */
    DataModel<D> getModel();

    /**
     * Java Object from the bound data.
     *
     * @return bound data object
     */
    D getValue();

    /**
     * Data bound to the requested field.
     *
     * @param name field name
     * @return field binder for the provided field name
     */
    BoundField getField(String name);

    /**
     * Any fields that were in the template but could not be matched back to the
     * model.
     *
     * Extraction of unbound data is an optional feature for a DataTemplate and
     * if supported may not contain all of the unbounded data within a document.
     *
     * @return non-backed collection of unmatched fields or empty collection
     */
    Collection<UnboundData> getUnmatched();

}
