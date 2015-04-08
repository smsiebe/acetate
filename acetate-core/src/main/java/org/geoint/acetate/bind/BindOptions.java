package org.geoint.acetate.bind;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import org.geoint.acetate.bind.ComponentOptions.ComponentOptionsBuilder;
import org.geoint.acetate.bind.spi.BindingWriter;
import org.geoint.concurrent.Immutable;
import org.geoint.exception.ExceptionHandler;

/**
 * Binding options augment the data binding process.
 * <p>
 * Binding options can be used to augment source binder, the binding engine, and
 * optionally the destination binder, allowing run-time customization of a data
 * model for a specific binding operation, without affecting other binding
 * operations using the same model.
 */
public interface BindOptions {

    /**
     * Retrieve all component path aliases.
     *
     * If a component does not have any aliases it will not be found in the
     * returned map.
     *
     * The returned map is immutable. Any calls to manipulate the content of the
     * Map will fail to update the binding options any may result in exceptions
     * being thrown.
     *
     * @return immutable map of aliases; key is the component path, value is a
     * collection of aliased paths for that component
     */
    @Immutable
    Map<String, @Immutable Collection<String>> getAliases();

    /**
     * Return all the component path aliases for a specific domain model
     * component.
     *
     * @param path component path
     * @return optional collection of aliases, if not aliases are found for the
     * component the returned Optional will not contain a collection
     */
    Optional<@Immutable Collection<String>> getAliases(String path);

    /**
     * Resolve an alias to the component path.
     *
     * @param alias alias name
     * @return optional component path, if found
     */
    Optional<String> resolveAlias(String alias);

    /**
     * Returns the component options for the requested component.
     *
     * @param pathOrAlias component path or alias
     * @return optional component binding options
     */
    Optional<ComponentOptions> getComponentOptions(String pathOrAlias);

    /**
     * Returns the writer used for data not mappable to the domain model (sparse
     * data).
     *
     * @return optional sparse data writer
     */
    Optional<BindingWriter> getSparseWriter();

    /**
     * Returns the handler to use for binding errors.
     *
     * If the exception is swawllowed (not-rethrown) the binder will continue
     * despite of the exception. If it is rethrown the binder will stop.
     *
     * @return optional error handler
     */
    Optional<ExceptionHandler<DataBindException>> getErrorHandler();

    /**
     * Returns the handler to use for binding warnings.
     *
     * If the handler re-throws a warning exception the exception will then be
     * processed as an error.
     *
     * @return optional warning handler
     */
    Optional<ExceptionHandler<DataBindException>> getWarningHandler();

    public static interface BindOptionsBuilder {

        /**
         * Add a model component alias, allowing data to also be bound for the
         * defined alias.
         *
         * @param path data model component path
         * @param aliasPath alias model component path
         * @return this (fluid interface)
         */
        BindOptionsBuilder alias(String path, String aliasPath);

        /**
         * Returns the ComponentOptions for a specified component path.
         *
         * @param path component path
         * @return component options for the model component
         */
        ComponentOptionsBuilder component(String path);

        /**
         * Adds a writer to called for data that was read from a data binder but
         * did not have a corresponding field in the data model.
         *
         * For bind operations that do not create a {@link BoundData} instance
         * this is the lightest-weight way to collect the sparse data from a
         * data binding (as opposed to requesting the BoundData directly).
         *
         *
         * @param sparseWriter interface to write the sparse data
         * @return this (fluid interface)
         */
        BindOptionsBuilder setSparseWriter(BindingWriter sparseWriter);

        /**
         * Sets an error handler to intercept exceptions that would otherwise
         * cause the binding operation to fail.
         *
         * The may only be one error handler that may either re-throw the
         * exception, which will terminate the binding operation, or "swallow"
         * the exception allowing the binding operation to continue.
         *
         * @param handler fatal exception handler
         * @return this (fluid interface)
         */
        BindOptionsBuilder setErrorHandler(
                ExceptionHandler<DataBindException> handler);

        /**
         * Sets a warning handler to intercept exceptions that gets logged but
         * not cause the binding operation to fail.
         *
         * Even with a warning handler set the warning exception will still be
         * logged.
         *
         * @param handler warning exception handler
         * @return this (fluid interface)
         */
        BindOptionsBuilder setWarningHandler(
                ExceptionHandler<DataBindException> handler);

        /**
         * Create binding options instance.
         *
         * @return options instance
         */
        BindOptions build();
    }
}
