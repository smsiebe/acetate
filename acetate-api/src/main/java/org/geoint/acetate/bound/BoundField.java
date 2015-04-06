package org.geoint.acetate.bound;

import org.geoint.acetate.model.FieldModel;

/**
 * Binding of data to a {@link FieldModel}.
 *
 * @param <T> data type of the field
 */
public interface BoundField<T> extends BoundComponent<FieldModel<T>> {

    /**
     * Data value as object.
     *
     * @return data value as object
     */
    T asObject();

    /**
     * Data value as binary.
     *
     * @return data value as bytes
     */
    byte[] asBytes();

    /**
     * Data value as String.
     *
     * @return data value as String
     */
    String asString();

}
