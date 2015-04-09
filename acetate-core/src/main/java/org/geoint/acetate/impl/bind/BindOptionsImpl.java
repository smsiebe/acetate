package org.geoint.acetate.impl.bind;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.geoint.acetate.bind.BindOptions;
import org.geoint.acetate.bind.ComponentOptions;
import org.geoint.acetate.bind.DataBindException;
import org.geoint.acetate.bind.BindingWriter;
import org.geoint.acetate.bound.BoundData;
import org.geoint.exception.ExceptionHandler;
import org.geoint.util.collection.CustomMultiMap;
import org.geoint.util.collection.MultiMap;

/**
 *
 */
public class BindOptionsImpl implements BindOptions {

    public static final BindOptions DEFAULT
            = new BindOptionsImpl(Collections.EMPTY_MAP,
                    Collections.EMPTY_MAP, null, null, null);

    //key is component path, value is a collection of aliases
    private final Map<String, Collection<String>> aliases;
    //key is alias, value is component path
    private final Map<String, String> reverseAliases;
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
        this.reverseAliases = reverseAliases;

        MultiMap<String, String> multiAliases = new CustomMultiMap(
                () -> new HashMap<>(), () -> new ArrayList<>()
        );

        //remember: the key is the alias and value is the component name 
        //          in the reverseAliases
        reverseAliases.entrySet().stream()
                .forEach((e) -> multiAliases.add(e.getValue(), e.getKey()));

        this.aliases = Collections.unmodifiableMap(multiAliases);
    }

    public static final BindOptionsBuilder builder() {
        return new BindOptionsBuilderImpl();
    }

    @Override
    public Map<String, Collection<String>> getAliases() {
        return aliases;
    }

    @Override
    public Optional<Collection<String>> getAliases(String path) {
        return Optional.ofNullable(aliases.get(path));
    }

    @Override
    public Optional<String> resolveAlias(String alias) {
        return Optional.ofNullable(reverseAliases.get(alias));
    }

    @Override
    public Optional<ComponentOptions> getComponentOptions(String pathOrAlias) {
        String path = null;
        if (componentOptions.containsKey(pathOrAlias)) {
            path = pathOrAlias;
        } else {
            //lookup alias 
            Optional<String> resolvedPath = resolveAlias(pathOrAlias);
            if (resolvedPath.isPresent()) {
                path = resolvedPath.get();
            }
        }

        return Optional.ofNullable(
                (path != null) ? componentOptions.get(path) : null
        );
    }

    @Override
    public Optional<BindingWriter> getSparseWriter() {
        return sparseWriter;
    }

    @Override
    public Optional<ExceptionHandler<DataBindException>> getErrorHandler() {
        return errorHandler;
    }

    @Override
    public Optional<ExceptionHandler<DataBindException>> getWarningHandler() {
        return warningHandler;
    }

    public static class BindOptionsBuilderImpl implements BindOptionsBuilder {

        //alias reverse lookup (key is alias, value is the real component path)
        private final Map<String, String> reverseAliases = new HashMap<>();
        private final Map<String, ComponentOptionsBuilder> componentOptions
                = new HashMap<>();
        private BindingWriter sparseWriter;
        private ExceptionHandler<DataBindException> warningHandler;
        private ExceptionHandler<DataBindException> errorHandler;

        private BindOptionsBuilderImpl() {
        }

        /**
         * Add a model component alias, allowing data to also be bound for the
         * defined alias.
         *
         * @param path data model component path
         * @param aliasPath alias model component path
         * @return this (fluid interface)
         */
        @Override
        public BindOptionsBuilderImpl alias(String path, String aliasPath) {
            reverseAliases.put(aliasPath, path);
            return this;
        }

        /**
         * Returns the ComponentOptions for a specified component path.
         *
         * @param path component path
         * @return component options for the model component
         */
        @Override
        public ComponentOptionsBuilder component(String path) {
            if (!componentOptions.containsKey(path)) {
                componentOptions.put(path, ComponentOptionsImpl.builder());
            }
            return componentOptions.get(path);
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
        @Override
        public BindOptionsBuilderImpl setSparseWriter(BindingWriter sparseWriter) {
            this.sparseWriter = sparseWriter;
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
        @Override
        public BindOptionsBuilderImpl setErrorHandler(
                ExceptionHandler<DataBindException> handler) {
            this.errorHandler = handler;
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
        @Override
        public BindOptionsBuilderImpl setWarningHandler(
                ExceptionHandler<DataBindException> handler) {
            this.warningHandler = handler;
            return this;
        }

        @Override
        public BindOptions build() {

        }
    }

}
