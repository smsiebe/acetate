package org.geoint.acetate.template;

import java.io.IOException;
import java.io.OutputStream;
import org.geoint.acetate.metamodel.DataModel;

/**
 * A template which can create a mime-specific schema based on the
 * {@link DataModel} and a specific template.
 */
public interface SchemaGeneratingTemplate {

    /**
     * Creates a mime type specific schema based on the template and model.
     *
     * @param model model with which to bind the template
     * @param out stream to write the schema
     * @throws IOException if there are problems writing to the stream
     * @throws UnsupportedOperationException if not supported
     */
    void writeSchema(DataModel<?> model, OutputStream out)
            throws IOException, UnsupportedOperationException;
}
