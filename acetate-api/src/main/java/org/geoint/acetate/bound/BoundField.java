package org.geoint.acetate.bound;

import org.geoint.acetate.model.FieldModel;

/**
 * Binding of data to a {@link FieldModel}.
 *
 * @param <T> data type of the field
 */
public interface BoundField<T> {

    /**
     * Data value.
     *
     * @return data value
     */
    T getValue();

    /**
     * Data model of the field.
     *
     * @return field model or null if the field is not bound to a known model
     * component
     */
    FieldModel<T> getModel();

}
