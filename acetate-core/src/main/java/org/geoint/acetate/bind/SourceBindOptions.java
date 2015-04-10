package org.geoint.acetate.bind;

import java.io.OutputStream;
import org.geoint.acetate.bound.sparse.SparseWriter;
import org.geoint.acetate.model.DataModel;
import org.geoint.acetate.transform.DataFormatter;

/**
 *
 */
public interface SourceBindOptions extends BindOptions {

    @Override
    public SourceBindOptions errorHandler(BindExceptionHandler handler);

    @Override
    public SourceBindOptions warningHandler(BindExceptionHandler handler);

    @Override
    public SourceBindOptions sparseWriter(SparseWriter sparseWriter);

    @Override
    public SourceBindOptions alias(String path, String aliasPath);

    @Override
    public SourceBindOptions ignore(String path);

    @Override
    public SourceBindOptions reformat(String componentPath, DataFormatter formatter);

    DestinationBindOptions to(DataWriter destination);

    DestinationBindOptions to(DataModel model, DataWriter destination);

    DestinationBindOptions to(DataBinder binder, OutputStream out);

}
