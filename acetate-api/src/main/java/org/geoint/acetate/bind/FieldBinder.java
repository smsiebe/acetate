package org.geoint.acetate.bind;

import org.geoint.acetate.codec.AcetateTransformException;
import org.geoint.acetate.metamodel.FieldModel;

/**
 * An object used to access and set data values on an data object.
 *
 * @param <D> the data item type that contains this field
 * @param <F> the field data type
 * @param <T> the field data type after encoding
 */
public interface FieldBinder<D, F, T> {

    /**
     * The model used for this field.
     *
     * @return mode for this field
     */
    FieldModel<D, F, T> getField();

    /**
     * Return the value of the field before encoding.
     *
     * @return the value of the field
     */
    F get();

    /**
     * Encoded value of this object, as defined by the fields codec, if any.
     *
     * @return the encoded value of the object
     * @throws AcetateTransformException if there were errors transforming
     */
    T getEncoded() throws AcetateTransformException;

}
