package org.geoint.acetate.model;

import org.geoint.acetate.data.DataCodec;

/**
 *
 * @param <T>
 */
public interface ValueModel<T> extends DataModel<T> {

    DataCodec<T> getCodec();

}
