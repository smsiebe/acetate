package org.geoint.acetate.template;

import java.io.IOException;
import java.io.InputStream;
import org.geoint.acetate.bind.BoundData;
import org.geoint.acetate.codec.AcetateCodecException;
import org.geoint.acetate.metamodel.DataModel;

/**
 * A DataTemplate which can read
 */
public interface ReadTemplate {

//    <T> BoundData<T> read(DataModel<T> model, InputStream in,
//            TemplateFilter... filters) throws IOException, AcetateCodecException;
    
    <T> BoundData<T> read(DataModel<T> model, InputStream in)
            throws IOException, AcetateCodecException;
}
