package org.geoint.acetate.bind;

import org.geoint.acetate.bound.sparse.SparseWriter;
import org.geoint.acetate.transform.DataFormatter;

/**
 */
public interface BindOptions {

    /**
     * Reformat the data value of the component.
     *
     * @param componentPath path
     * @param formatter formatter
     * @return this (fluid interface)
     */
    BindOptions reformat(String componentPath, DataFormatter formatter);

    /**
     * Ignore a specific component on the model for this binding operation.
     *
     * @param path component path
     * @return this (fluid interface)
     */
    BindOptions ignore(String path);

    /**
     * Add a model component alias, allowing data to also be bound for the
     * defined alias.
     *
     * @param path data model component path
     * @param aliasPath alias model component path
     * @return this (fluid interface)
     */
    BindOptions alias(String path, String aliasPath);

    /**
     * Sets the writer that is called for data is not represented in either the
     * source or destination data model.
     *
     *
     * @param sparseWriter interface to write the sparse data
     * @return this (fluid interface)
     */
    BindOptions sparseWriter(SparseWriter sparseWriter);

    /**
     * Sets a warning handler to intercept exceptions that gets logged but not
     * cause the binding operation to fail.
     *
     * There may only be one warning handler.
     *
     * Even with a warning handler set the warning exception will still be
     * logged.
     *
     * @param handler warning exception handler
     * @return this (fluid interface)
     */
    BindOptions warningHandler(BindExceptionHandler handler);

    /**
     * Sets an error handler to intercept exceptions that would otherwise cause
     * the binding operation to fail.
     *
     * The may only be one error handler that may either re-throw the exception,
     * which will terminate the binding operation, or "swallow" the exception
     * allowing the binding operation to continue.
     *
     * @param handler fatal exception handler
     * @return this (fluid interface)
     */
    BindOptions errorHandler(BindExceptionHandler handler);

}
