package org.geoint.acetate.bind.object;

import java.io.InputStream;
import java.io.OutputStream;
import org.geoint.acetate.bind.DataBinder;

/**
 *
 */
public interface ObjectDataBinder<T> extends DataBinder {

    @Override
    public ObjectDataReader reader(InputStream in);

    ObjectDataReader<T> reader(T data);

    @Override
    public ObjectDataWriter writer(OutputStream out);

    ObjectDataWriter<T> writer();
}
