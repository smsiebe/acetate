package org.geoint.acetate.data;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Binds data values with a data model, providing a means access the data within
 * the context of the model.
 *
 */
public interface Data {

    void asBytes(OutputStream out) throws IOException;

    void visit(DataVisitor visitor);

}
