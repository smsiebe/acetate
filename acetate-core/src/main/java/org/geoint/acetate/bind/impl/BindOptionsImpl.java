package org.geoint.acetate.bind.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.geoint.acetate.bind.BindOptions;
import org.geoint.acetate.bind.Binder;
import org.geoint.acetate.bind.ComponentOptions;
import org.geoint.acetate.bind.DataBindException;
import org.geoint.acetate.bind.spi.BindingWriter;
import org.geoint.acetate.bound.BoundData;
import org.geoint.exception.ExceptionHandler;

/**
 *
 */
public class BindOptionsImpl implements BindOptions {

    public static final BindOptions DEFAULT
            = new BindOptionsImpl(Collections.EMPTY_MAP,
                    Collections.EMPTY_MAP, null, null, null);

    private final Map<String, Collection<String>> aliases;
    private final Map<String, ComponentOptions> componentOptions;
    private final Optional<BindingWriter> sparseWriter;
    private final Optional<ExceptionHandler<DataBindException>> warningHandler;
    private final Optional<ExceptionHandler<DataBindException>> errorHandler;

    /**
     *
     * @param reverseAliases
     * @param components
     * @param sparseWriter
     * @param errorHandler optional (nullable) error handler
     * @param warningHandler optional (nullable) warning handler
     */
    private BindOptionsImpl(Map<String, String> reverseAliases,
            Map<String, ComponentOptions> componentOptions,
            BindingWriter sparseWriter,
            ExceptionHandler<DataBindException> warningHandler,
            ExceptionHandler<DataBindException> errorHandler) {

        this.errorHandler = Optional.ofNullable(errorHandler);
        this.warningHandler = Optional.ofNullable(warningHandler);
        this.sparseWriter = Optional.ofNullable(sparseWriter);
        this.componentOptions = Collections.unmodifiableMap(componentOptions);

        reverseAliases.entrySet().stream()
                .collect(Collectors.t)
    }

    @Override
    public Map<String, Collection<String>> getAliases() {

    }

    @Override
    public Optional<Collection<String>> getAliases(String path) {

    }

    @Override
    public Optional<BindingWriter> getSparseWriter() {

    }

    @Override
    public Optional<ExceptionHandler<DataBindException>> getErrorHandler() {

    }

    @Override
    public Optional<ExceptionHandler<DataBindException>> getWarningHandler() {

    }

    public static class BindOptionsBuilder {

        //alias reverse lookup (key is alias, value is the real component path)
        private final Map<String, String> reverseAliases = new HashMap<>();

        private BindOptionsBuilder() {
        }

        /**
         * Add a model component alias, allowing data to also be bound for the
         * defined alias.
         *
         * @param path data model component path
         * @param aliasPath alias model component path
         * @return this (fluid interface)
         */
        public BindOptionsBuilder alias(String path, String aliasPath) {
            options.aliases.put(path, aliasPath);
            return this;
        }

        /**
         * Returns the ComponentOptions for a specified component path.
         *
         * @param path component path
         * @return component options for the model component
         */
        public ComponentOptions component(String path) {
            if (!options.components.containsKey(path)) {
                options.components.put(path, new ComponentOptionsImpl());
            }
            return options.components.get(path);
        }

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
        public BindOptionsBuilder setSparseWriter(BindingWriter sparseWriter) {
            options.sparseWriter = Optional.ofNullable(sparseWriter);
            return this;
        }

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
        public BindOptionsBuilder setErrorHandler(
                ExceptionHandler<DataBindException> handler) {
            options.errorHandler = Optional.ofNullable(handler);
            return this;
        }

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
        public BindOptionsBuilder setWarningHandler(
                ExceptionHandler<DataBindException> handler) {

            options.warningHandler = Optional.ofNullable(handler);
            return this;
        }
    }

}
