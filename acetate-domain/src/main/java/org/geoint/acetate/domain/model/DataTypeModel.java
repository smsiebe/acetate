package org.geoint.acetate.domain.model;

import org.geoint.acetate.domain.annotation.Accessor;
import org.geoint.acetate.domain.annotation.DataType;

/**
 * Model of a domain data type.
 *
 * @param <T> java type of the value data
 * @see DataType
 */
public interface DataTypeModel<T> extends DataModel<T> {

    @Accessor
    CodecModel<T> getCodecModel();

    @Accessor
    ComparatorModel<T> getComparatorModel();

}
