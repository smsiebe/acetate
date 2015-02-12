package org.geoint.acetate.metamodel;

import org.geoint.acetate.codec.AcetateCodec;

/**
 * Methods that allow for the modification of a {@link DataModel}.
 *
 * Note that DataModel instances must be immutable, so any MutableModel must be
 * a decorated/proxy model.
 *
 * @param <T> root class type modeled
 */
public interface MutableModel<T> {

    /**
     * Adds a new field to the data model.
     *
     * Overwriting a field does not remove any aliases that were previously
     * associated with this field name.
     *
     * @param <P> parent/container object type of the field
     * @param <T> field data type
     * @param name absolute name of the field
     * @param accessor method to retrieve the field value
     * @param setter method to set the field value
     * @return model replaced by this method call or null if there wasn't a
     * model previously for this field name
     */
    <P, T> FieldModel<?, ?> setField(String name, FieldAccessor<P, T> accessor,
            FieldSetter<P, T> setter);

    /**
     * Removes a field, and all aliases, from the data model.
     *
     * @param name absolute name the field (any alias)
     * @return the model removed or null if no field was found by that name
     */
    FieldModel<?, ?> removeField(String name);

    /**
     * Adds an alias the field could also be named.
     *
     * @param name absolute name of the field (any existing alias)
     * @param aliases new absolute alias names for the field
     * @return the model of the field which the alias was assigned or null if no
     * field was found by the name provided
     */
    FieldModel<?, ?> addAlias(String name, String... aliases);

    /**
     * Adds a codec to a specific field.
     *
     * @param <T> to type
     * @param <F> from type
     * @param fieldName absolute field name
     * @param codec codec to set for this field
     * @return the model the codec was applied, or null if there is not field by
     * this name in the model
     */
    <T,F> FieldModel<?, T> setCodec(String fieldName, AcetateCodec<T, F> codec);

    /**
     * Adds a codec that is used for any field that returns the type defined by
     * this codec.
     *
     * @param codec codec to apply to any field values with the correct type
     */
    void addCodec(AcetateCodec<?, ?> codec);

}
