package org.geoint.acetate.metamodel;

import java.util.Collection;
import org.geoint.acetate.codec.AcetateCodec;

/**
 * Hierarchical data field node which models its own structure as well as
 * provides access to its child fields (if present).
 *
 * @param <D> data item type that contains this field.
 * @param <F> the data type of the source java object
 * @param <T> the encoded data type
 */
public interface FieldModel<D, F, T> extends StructuredModel {

    /**
     * The mutator/setter for the field.
     *
     * @return field setter
     */
    FieldSetter<D, T> getSetter();

    /**
     * Optional field codec.
     *
     * @return codec, if set, or null
     */
    AcetateCodec<F, T> getCodec();
}
