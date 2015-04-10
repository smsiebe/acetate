package org.geoint.acetate.bind;

import java.io.InputStream;
import java.io.OutputStream;
import org.geoint.acetate.model.DataModel;

/**
 *
 */
public interface DataBinder {

    DataModel getModel();

    DataReader reader(InputStream in);

    DataWriter writer(OutputStream out);

}
