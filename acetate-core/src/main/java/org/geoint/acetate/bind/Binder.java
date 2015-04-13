package org.geoint.acetate.bind;

import org.geoint.acetate.bind.object.ObjectDataBinder;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import org.geoint.acetate.bind.bound.BoundDataBinder;
import org.geoint.acetate.bound.BoundData;
import org.geoint.acetate.bound.sparse.SparseWriter;
import org.geoint.acetate.model.DataModel;
import org.geoint.acetate.transform.DataFormatter;
import org.geoint.concurrent.ThreadSafe;
import org.geoint.util.collection.CustomMultiMap;
import org.geoint.util.collection.MultiMap;

/*
 * This class layout might be a little hard to understand...it was done this 
 * way to create a cleaner/fluid Binder API.
 *
 */
/**
 *
 */
@ThreadSafe
public final class Binder {

    private final SourceBindContext source;
    private final DestinationBindContext dest;

    private Binder(SourceBindContext source, DestinationBindContext dest) {
        this.source = source;
        this.dest = dest;
    }

    /**
     * Bind source reader to destination writer.
     */
    public void execute() {

    }

    public static SourceBindOptions source(DataModel model,
            DataReader reader) {
        return new SourceBindContext(model, reader);
    }

    public static SourceBindOptions source(DataBinder binder,
            InputStream in) {
        return source(binder.getModel(), binder.reader(in));
    }

    public static <T> SourceBindOptions source(ObjectDataBinder<T> binder,
            T object) {
        return source(binder.getModel(), binder.reader(object));
    }

    public static SourceBindOptions source(BoundDataBinder binder,
            BoundData bound) {
        return source(binder.getModel(), binder.reader(bound));
    }

    /**
     * Encapsulates the source/destination binding, exposed publically as
     * BindOptions fluid binding interface.
     */
    private static abstract class BindContext implements BindOptions {

        protected final Map<String, String> aliases;
        protected final Set<String> ignore;
        protected final MultiMap<String, DataFormatter> formatters;
        protected DataModel model;
        protected SparseWriter sparseWriter;
        protected BindExceptionHandler warningHandler;
        protected BindExceptionHandler errorHandler;

        private BindContext(DataModel model) {
            this.model = model;
            this.aliases = new HashMap<>();
            this.ignore = new HashSet<>();
            this.formatters = new CustomMultiMap<>(
                    () -> new HashMap<>(),
                    () -> new LinkedList<>()
            );
        }

        @Override
        public BindOptions sparseWriter(SparseWriter sw) {
            sparseWriter = sw;
            return this;
        }

        @Override
        public BindOptions warningHandler(BindExceptionHandler handler) {
            warningHandler = handler;
            return this;
        }

        @Override
        public BindOptions errorHandler(BindExceptionHandler handler) {
            errorHandler = handler;
            return this;
        }

        @Override
        public BindOptions alias(String path, String aliasPath) {
            this.aliases.put(path, aliasPath);
            return this;
        }

        @Override
        public BindOptions ignore(String path) {
            this.ignore.add(path);
            return this;
        }

        @Override
        public BindOptions reformat(String componentPath,
                DataFormatter formatter) {
            formatters.add(componentPath, formatter);
            return this;
        }
    }

    private static class SourceBindContext extends BindContext
            implements SourceBindOptions {

        public SourceBindContext(DataModel model) {
            super(model);
        }

        @Override
        public DestinationBindOptions to(DataWriter destination) {
            //source and destination model are the same

            DestinationBindOptions dest
                    = new DestinationBindContext(model, destination);

        }

        @Override
        public DestinationBindOptions to(DataModel model,
                DataWriter destination) {
            DestinationBindOptions dest = new DestinationBindContext(model,
                    destination);

        }

        @Override
        public DestinationBindOptions to(DataBinder binder, OutputStream out) {
            return to(binder.getModel(), binder.writer(out));
        }

        @Override
        public SourceBindOptions alias(String path, String aliasPath) {
            super.alias(path, aliasPath);
            return this;
        }

        @Override
        public SourceBindOptions ignore(String path) {
            super.ignore(path);
            return this;
        }

        @Override
        public SourceBindOptions errorHandler(BindExceptionHandler handler) {
            super.errorHandler(handler);
            return this;
        }

        @Override
        public SourceBindOptions warningHandler(BindExceptionHandler handler) {
            super.warningHandler(handler);
            return this;
        }

        @Override
        public SourceBindOptions sparseWriter(SparseWriter sparseWriter) {
            super.sparseWriter(sparseWriter);
            return this;
        }

        @Override
        public SourceBindOptions reformat(String componentPath,
                DataFormatter formatter) {
            super.reformat(componentPath, formatter);
            return this;
        }

    }

    private static class DestinationBindContext extends BindContext
            implements DestinationBindOptions {

        private final Map<String, Supplier> constants;

        public DestinationBindContext(DataModel model) {
            super(model);
            this.constants = new HashMap<>();
        }

        @Override
        public void bind() {
            execute();
        }

        @Override
        public DestinationBindOptions constant(String path, Supplier supplier) {
            this.constants.put(path, supplier);
            return this;
        }

        @Override
        public DestinationBindOptions errorHandler(BindExceptionHandler handler) {
            super.errorHandler(handler);
            return this;
        }

        @Override
        public DestinationBindOptions warningHandler(BindExceptionHandler handler) {
            super.warningHandler(handler);
            return this;
        }

        @Override
        public DestinationBindOptions sparseWriter(SparseWriter sparseWriter) {
            super.sparseWriter(sparseWriter);
            return this;
        }

        @Override
        public DestinationBindOptions alias(String path, String aliasPath) {
            super.alias(path, aliasPath);
            return this;
        }

        @Override
        public DestinationBindOptions ignore(String path) {
            super.ignore(path);
            return this;
        }

        @Override
        public DestinationBindOptions reformat(String componentPath,
                DataFormatter formatter) {
            super.reformat(componentPath, formatter);
            return this;
        }
    }
}
