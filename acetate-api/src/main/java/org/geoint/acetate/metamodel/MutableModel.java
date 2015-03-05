package org.geoint.acetate.metamodel;

import org.geoint.acetate.codec.AcetateCodec;

/**
 * Methods that allow for the modification of a {@link DataModel}.
 *
 * Note that DataModel instances must be immutable, so any MutableModel must be
 * a decorated/proxy model.
 *
 * @param <R> root class type modeled
 */
public interface MutableModel<R> extends DataModel<R> {

    /**
     * Adds a new field to the data model.
     *
     * Overwriting a field does not remove any aliases that were previously
     * associated with this field name.
     *
     * @param <P> parent/container object type of the field
     * @param <T> field data type
     * @param path absolute path of the model component
     * @param accessor method to retrieve the field value
     * @param setter method to set the field value
     * @return model replaced by this method call or null if there wasn't a
     * model previously for this field name
     */
    <P, T> ModelField<P, T> setField(String path, FieldAccessor<P, T> accessor,
            FieldSetter<P, T> setter);

    /**
     * Removes a field, and all aliases, from the data model.
     *
     * @param name absolute path of the component (any alias)
     * @return the model removed or null if no field was found by that name
     */
    ModelComponent<?> removeComponent(String name);

    /**
     * Adds an alias the field could also be named.
     *
     * @param name absolute path of the component (any existing alias)
     * @param aliases new absolute alias names for the field
     * @return the model of the field which the alias was assigned or null if no
     * field was found by the name provided
     */
    ModelComponent<?> addAlias(String name, String... aliases);

    /**
     * Adds a codec to a specific field.
     *
     * @param <P> java object type to which this codec can apply
     * @param <T> to type
     * @param componentPath absolute component path to apply the codec
     * @param codec codec to set for this field
     * @return the model the codec was applied, or null if there is not field by
     * this name in the model
     */
    <P, T> ModelField<P, T> setCodec(String componentPath, AcetateCodec<?, T> codec);

    /**
     * Adds a codec to the model which is used on any any field would normally
     * return the type defined as the source type of this codec.
     *
     * @param codec codec to apply to any field values with the correct type
     */
    void addCodec(AcetateCodec<?, ?> codec);

}
