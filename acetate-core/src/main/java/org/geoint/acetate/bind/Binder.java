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
 * Binder makes purposeful use of the Java inner-class capabilities to 
 * acces the private fields of the outer class (and vice-versa).  This is how 
 * all three of the  BindOptionsImpl inner classes work, providing a fluid 
 * facade but the Binder instance being configured.  Binder is the class 
 * that actually does the work (#doBind).
 */
/**
 *
 */
@ThreadSafe
public final class Binder {

    private final DataModel sourceModel;
    private final DataReader sourceReader;
    private final SourceBindOptions sourceOptions
            = new SourceBindOptionsImpl();
    private DataModel destModel;
    private DataWriter destWriter;
    private final DestinationBindOptions destOptions
            = new DestinationBindOptionsImpl();
    private SparseWriter sparseWriter;
    private BindExceptionHandler warningHandler;
    private BindExceptionHandler errorHandler;

    public Binder(DataModel sourceModel, DataReader sourceReader) {
        this.sourceModel = sourceModel;
        this.sourceReader = sourceReader;
    }

    /**
     * Do the actual work...bind from source to destination.
     */
    private void doBind() {
        bvbcvbc
    }

    public static SourceBindOptions source(DataModel model,
            DataReader reader) {
        Binder binder = new Binder(model, reader);
        return binder.sourceOptions;
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

    private abstract class BindOptionsImpl implements BindOptions {

        private final Map<String, String> aliases;
        private final Set<String> ignore;
        private final MultiMap<String, DataFormatter> formatters;

        private BindOptionsImpl() {
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

    private class SourceBindOptionsImpl extends BindOptionsImpl
            implements SourceBindOptions {

        @Override
        public DestinationBindOptions to(DataWriter destination) {
            return to(sourceModel, destination);
        }

        @Override
        public DestinationBindOptions to(DataModel model, DataWriter destination) {
            destModel = model;
            destWriter = destination;
            return destOptions;
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

    private class DestinationBindOptionsImpl extends BindOptionsImpl
            implements DestinationBindOptions {

        private final Map<String, Supplier> constants;

        public DestinationBindOptionsImpl() {
            this.constants = new HashMap<>();
        }

        @Override
        public void bind() {
            doBind();
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
