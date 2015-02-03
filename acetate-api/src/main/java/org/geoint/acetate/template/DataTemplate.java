package org.geoint.acetate.template;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.geoint.acetate.bind.BoundData;
import org.geoint.acetate.codec.AcetateCodecException;
import org.geoint.acetate.metamodel.DataModel;

/**
 * Templates are a specialized format-aware bidirectional structured data
 * binder, mapping data from a document to an instance or an instance of a data
 * document.
 *
 * Templates can also (optionally) create schemas based on their data format.
 */
public interface DataTemplate {

    <T> BoundData<T> read(DataModel<T> model, InputStream in)
            throws IOException, AcetateCodecException;

//    <T> BoundData<T> read(DataModel<T> model, InputStream in,
//            TemplateFilter... filters) throws IOException, AcetateCodecException;

    void write(BoundData<?> data, OutputStream out)
            throws IOException, AcetateCodecException;

//    void write(BoundData<?> data, OutputStream out, TemplateFilter... filters)
//            throws IOException, AcetateCodecException;

    /**
     * Optional method; creates a mime type specific schema based on the
     * template and model.
     *
     * @param model model with which to bind the template
     * @param out stream to write the schema
     * @throws IOException if there are problems writing to the stream
     * @throws UnsupportedOperationException if not supported
     */
    void writeSchema(DataModel<?> model, OutputStream out)
            throws IOException, UnsupportedOperationException;

    boolean isSchemaSupported();

}
