package org.geoint.acetate.bind;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
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
    Map<String, Collection<String>> getAliases();

    /**
     * Return all the component path aliases for a specific domain model
     * component.
     *
     * @param path component path
     * @return optional collection of aliases, if not aliases are found for the
     * component the returned Optional will not contain a collection
     */
    @Immutable
    Optional<Collection<String>> getAliases(String path);

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

}
