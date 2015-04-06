package org.geoint.acetate.bind;

import org.geoint.acetate.bind.spi.BindExceptionHandler;
import org.geoint.acetate.bind.spi.BindingWriter;
import org.geoint.acetate.bound.BoundData;

/**
 * Binding options that augment the behavior of a bind operation.
 * <p>
 * Binding options can be used to augment source binder, the binding engine, and
 * optionally the destination binder, allowing run-time customization of a data
 * model for a specific binding operation, without affecting other binding
 * operations using the same model.
 */
public interface BindOptions {

    /**
     * Add a model component alias, allowing data to also be bound for the
     * defined alias.
     *
     * @param path data model component path
     * @param aliasPath alias model component path
     * @return this (fluid interface)
     */
    public BindOptions alias(String path, String aliasPath);

    /**
     * Returns the ComponentOptions for a specified component path.
     *
     * @param path component path
     * @return component options for the model component
     */
    public ComponentOptions component(String path);

    /**
     * Adds a writer to called for data that was read from a data binder but did
     * not have a corresponding field in the data model.
     *
     * For bind operations that do not create a {@link BoundData} instance this
     * is the lightest-weight way to collect the sparse data from a data binding
     * (as opposed to requesting the BoundData directly).
     *
     *
     * @param sparseWriter interface to write the sparse data
     * @return this (fluid interface)
     */
    public BindOptions setSparseWriter(BindingWriter sparseWriter);

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
    public BindOptions setErrorHandler(BindExceptionHandler handler);

    /**
     * Sets a warning handler to intercept exceptions that gets logged but not
     * cause the binding operation to fail.
     *
     * Even with a warning handler set the warning exception will still be
     * logged.
     *
     * @param handler warning exception handler
     * @return this (fluid interface)
     */
    public BindOptions setWarningHandler(BindExceptionHandler handler);
}
