package org.geoint.acetate.bind;

import org.geoint.acetate.model.DataModel;
import org.geoint.acetate.model.ValueModel;

/**
 * Writer for structured (defined by a DataModel) data.
 *
 * @param <T> data model type
 */
public interface StructuredDataWriter<T> extends DataWriter {

    void startObject(DataModel<T> model) throws DataBindException;

    void startArray(DataModel<T> model) throws DataBindException;

    <V> void write(ValueModel<V> model, V value) throws DataBindException;

}
