package org.geoint.acetate.bound;

import org.geoint.acetate.model.FieldModel;

/**
 * Binding of data to a {@link FieldModel}.
 *
 * @param <T> data type of the field
 */
public interface BoundField<T> {

    /**
     * Data model of the field.
     *
     * @return field model
     */
    FieldModel<T> getModel();

    /**
     * Data value.
     *
     * @return data value
     */
    T getValue();
}
