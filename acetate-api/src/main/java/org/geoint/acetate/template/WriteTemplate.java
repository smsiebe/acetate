package org.geoint.acetate.template;

import java.io.IOException;
import java.io.OutputStream;
import org.geoint.acetate.bind.BoundData;
import org.geoint.acetate.codec.AcetateCodecException;

/**
 * Templates are mime-specific structured data binders capable of converting
 * java objects (instances of a DataModel) to character or binary formats.
 *
 * Templates may be unidirectional (only capable of either reading or writing)
 * or bidirectional. Further, they may also be capable of producing a
 * mime-specific schema based on a DataModel and a template definition.
 *
 * @see ReadTemplate
 * @see SchemaGeneratingTemplate
 */
public interface WriteTemplate {

    void write(BoundData<?> data, OutputStream out)
            throws IOException, AcetateCodecException;

//    void write(BoundData<?> data, OutputStream out, TemplateFilter... filters)
//            throws IOException, AcetateCodecException;
}
