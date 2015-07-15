package org.geoint.acetate.data;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.WritableByteChannel;
import org.geoint.acetate.domain.model.DataModel;
import org.geoint.acetate.domain.model.DataVisitor;

/**
 * Binds data values with a data model, providing a means access the data within
 * the context of the model.
 *
 * @param <T> java object representation of this data
 */
public interface Data<T> {

    T asObject();

    DataModel<T> getModel();

    byte[] getBytes();

    void write(WritableByteChannel channel) throws IOException;

    void write(OutputStream out) throws IOException;

    void visit(DataVisitor<T> visitor);

}
