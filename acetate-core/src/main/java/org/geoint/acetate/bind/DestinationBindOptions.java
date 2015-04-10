package org.geoint.acetate.bind;

import java.util.function.Supplier;
import org.geoint.acetate.bound.sparse.SparseWriter;
import org.geoint.acetate.transform.DataFormatter;

/**
 *
 */
public interface DestinationBindOptions extends BindOptions {

    @Override
    public DestinationBindOptions errorHandler(BindExceptionHandler handler);

    @Override
    public DestinationBindOptions warningHandler(BindExceptionHandler handler);

    @Override
    public DestinationBindOptions sparseWriter(SparseWriter sparseWriter);

    @Override
    public DestinationBindOptions alias(String path, String aliasPath);

    @Override
    public DestinationBindOptions ignore(String path);

    @Override
    public DestinationBindOptions reformat(String componentPath, DataFormatter formatter);

    DestinationBindOptions constant(String path, Supplier supplier);

    void bind();
}
