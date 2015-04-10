package org.geoint.acetate.bind.object;

import org.geoint.acetate.bind.DataBindException;
import org.geoint.acetate.bind.DataWriter;

/**
 *
 * @param <T>
 */
public interface ObjectDataWriter<T> extends DataWriter {

    T getObject() throws DataBindException;

}
