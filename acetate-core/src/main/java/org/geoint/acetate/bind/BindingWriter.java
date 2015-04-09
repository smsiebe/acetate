package org.geoint.acetate.bind;

import java.io.IOException;
import org.geoint.acetate.model.ClassModel;
import org.geoint.acetate.model.FieldModel;

/**
 * Marker interface for the writer for a binder.
 *
 * @param <T>
 */
public interface BindingWriter<T> {

    /**
     * Hints to the binder that the data to follow is in a data container.
     *
     * @throws IOException thrown if the binder could not write
     */
    void startArray() throws IOException;

    void endArray() throws IOException;

    void startClass(ClassModel model) throws IOException;

    void endClass() throws IOException;

    void field(FieldModel<?> model, T value) throws IOException;
}
