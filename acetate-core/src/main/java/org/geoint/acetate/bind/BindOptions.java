package org.geoint.acetate.bind;

import org.geoint.acetate.bind.ComponentOptions.ComponentOptionsBuilder;
import org.geoint.acetate.bound.BoundData;

/**
 * Binding options augments the data binding process.
 * <p>
 * Binding options is used to run-time customize the binding operation by making
 * Binder scoped changes to the DataModel or bound data, without affecting other
 * binding operations using the same model.
 *
 * @param <T> binding data type (characters, binary, object, etc)
 */
public interface BindOptions<T> {

    /**
     * Add a model component alias, allowing data to also be bound for the
     * defined alias.
     *
     * @param path data model component path
     * @param aliasPath alias model component path
     * @return this (fluid interface)
     */
    BindOptions<T> alias(String path, String aliasPath);

    /**
     * Sets the writer that is called for data is not represented in either the
     * source or destination data model.
     *
     *
     * @param sparseWriter interface to write the sparse data
     * @return this (fluid interface)
     */
    BindOptions<T> sparseHandler(BindingWriter<T> sparseWriter);

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
    BindOptions<T> errorHandler(BindExceptionHandler handler);

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
    BindOptions<T> warningHandler(BindExceptionHandler handler);

}
