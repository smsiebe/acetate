package org.geoint.acetate.template;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.geoint.acetate.bind.DataBinder;
import org.geoint.acetate.codec.AcetateTransformException;
import org.geoint.acetate.metamodel.DataModel;

/**
 * Templates are a specialized format-aware bidirectional data transformer,
 * mapping data from a document to an instance or an instance of a data
 * document.
 *
 * Templates can also (optionally) create schemas based on their data format.
 */
public interface DataTemplate {

    /**
     * Encodes a data item to the format defined by this template.
     *
     * @param <F> data type that is bound
     * @param binder the binding object
     * @param out where the data format is written
     * @throws AcetateTransformException if there are data transformation
     * problems
     * @throws IOException if there are problems writing
     */
    <F> void encode(DataBinder<F> binder, OutputStream out)
            throws AcetateTransformException, IOException;

    /**
     * Decode a data document into a data item using the template and data
     * model.
     *
     * @param <F> object type that is to be created
     * @param in formatted data information
     * @param model data model to use
     * @return bound data item
     * @throws AcetateTransformException if there are data transformation
     * problems
     * @throws IOException if there are problems reading
     */
    <F> DataBinder<F> decode(InputStream in, DataModel<F> model)
            throws AcetateTransformException, IOException;
}
