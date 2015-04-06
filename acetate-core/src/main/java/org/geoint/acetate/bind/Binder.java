package org.geoint.acetate.bind;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geoint.acetate.Acetate;
import org.geoint.acetate.bind.impl.BoundDataReader;
import org.geoint.acetate.bind.impl.BoundDataWriter;
import org.geoint.acetate.bind.spi.BinaryDataFormatter;
import org.geoint.acetate.bind.spi.BindExceptionHandler;
import org.geoint.acetate.bind.spi.BindingReader;
import org.geoint.acetate.bind.spi.BindingStep;
import org.geoint.acetate.bind.spi.BindingWriter;
import org.geoint.acetate.bind.spi.DataBinder;
import org.geoint.acetate.bind.spi.DataConverter;
import org.geoint.acetate.bind.spi.StringDataFormatter;
import org.geoint.acetate.bound.BoundData;
import org.geoint.acetate.model.DataModel;
import org.geoint.concurrent.ThreadSafe;

/**
 * Acetate binding engine.
 * <p>
 * The Binder engine can be used directly by applications to programmatically
 * bind acetate data binders to each other, however, it's recommended to look at
 * either the {@link Acetate higher-level programmatic API} or framework plugins
 * (ie JAX-RS) first as these will probably provide a "nicer" integration
 * experience.
 * <p>
 * A Binder instance <b>is</b> thread-safe.
 */
@ThreadSafe
public final class Binder {

    //locking so we don't reconfigure binding during a bind operation
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final ReadLock rl = lock.readLock();
    private final WriteLock wl = lock.writeLock();
    private final BindOptionsImpl options = new BindOptionsImpl();

    /**
     * Returns the binding options for this Binder instance.
     *
     * @return binding options
     */
    public BindOptions options() {
        return options;
    }

    /**
     * Binds to a generic object structure.
     * <p>
     * Unlike {@link #bind(DataBinder, DataBinder) } this method will always
     * produce a BoundData instance. If a BoundData instance is required, use
     * this method first than use {@link #bind(BoundData, DataBinder) }
     * to write from the bound data to the desired destination binder.
     *
     * @param model data model of the data
     * @param binder data source binder
     * @return bound data
     * @throws DataBindException thrown if there are problems binding the data
     */
    public BoundData bind(DataModel model, DataBinder binder)
            throws DataBindException {
        try {
            BoundDataWriter bdw = new BoundDataWriter();
            bind(model, binder.reader(), bdw);
            return bdw.getBoundData();
        } catch (IOException ex) {
            DataBindException bex = new DataBindException(binder.getClass(),
                    "Unable to retrieve reader from binder.",
                    ex);
            if (options.errorHandler.isPresent()) {
                options.errorHandler.get().handle(bex);
            }
            throw bex;
        }
    }

    /**
     * Binds data from the source binder to the destination binder.
     * <p>
     * This method attempts to bind data directly from one binder to another,
     * ideally first without the need to create a {@link BoundData} instance,
     * which would result in (unnecessarily) copying data into memory. Depending
     * on the binder types, option settings, etc this may be done anyway, but
     * we'll try to avoid it if we can.
     *
     * @param model model of the data
     * @param from data source binder
     * @param to data destination binder
     * @throws DataBindException thrown if there are problems binding the data
     */
    public void bind(DataModel model, DataBinder from, DataBinder to)
            throws DataBindException {
        BindingReader reader;
        BindingWriter writer;

        try {
            reader = from.reader();
        } catch (IOException ex) {
            DataBindException bex = new DataBindException(from.getClass(),
                    "Unable to retrieve reader from binder.",
                    ex);
            if (options.errorHandler.isPresent()) {
                options.errorHandler.get().handle(bex);
            }
            throw bex;
        }

        try {
            writer = to.writer();
        } catch (IOException ex) {

            DataBindException bex = new DataBindException(to.getClass(),
                    "Unable to retrieve writer from binder.",
                    ex);
            if (options.errorHandler.isPresent()) {
                options.errorHandler.get().handle(bex);
            }
            throw bex;
        }

        bind(model, reader, writer);
    }

    /**
     * Binds an instance of {@link BoundData} to the destination binder.
     *
     * @param bound bound data/model
     * @param to destination binder
     * @throws DataBindException thrown if there are problems binding the data
     */
    public void bind(BoundData bound, DataBinder to) throws DataBindException {
        BindingWriter writer;
        try {
            writer = to.writer();
        } catch (IOException ex) {
            DataBindException bex = new DataBindException(to.getClass(),
                    "Unable to retrieve writer from binder.",
                    ex);
            if (options.errorHandler.isPresent()) {
                options.errorHandler.get().handle(bex);
            }
            throw bex;
        }
        bind(bound.getModel(), new BoundDataReader(bound), writer);
    }

    /**
     * Bind from reader to writer using the model as a reference.
     *
     * @param model
     * @param reader
     * @param writer
     * @throws DataBindException
     */
    private void bind(DataModel model, BindingReader reader,
            BindingWriter writer) throws DataBindException {
        try {
            rl.lock();

        } finally {
            rl.unlock();
        }
    }

    private class BindOptionsImpl implements BindOptions {

        private final Map<String, String> aliases = new HashMap<>();
        private final Map<String, ComponentOptions> components
                = new HashMap<>();
        private Optional<BindingWriter> sparseWriter = Optional.empty();
        private Optional<BindExceptionHandler> errorHandler = Optional.empty();
        private Optional<BindExceptionHandler> warningHandler = Optional.empty();

        @Override
        public BindOptions alias(String path, String aliasPath) {
            try {
                wl.lock();
                this.aliases.put(path, aliasPath);
                return this;
            } finally {
                wl.unlock();
            }
        }

        @Override
        public ComponentOptions component(String path) {
            try {
                wl.lock();
                if (!components.containsKey(path)) {
                    components.put(path, new ComponentOptionsImpl());
                }
                return components.get(path);
            } finally {
                wl.lock();
            }
        }

        @Override
        public BindOptions setSparseWriter(BindingWriter sparseWriter) {
            try {
                wl.lock();
                this.sparseWriter = Optional.ofNullable(sparseWriter);
            } finally {
                wl.unlock();
            }
            return this;
        }

        @Override
        public BindOptions setErrorHandler(BindExceptionHandler handler) {
            try {
                wl.lock();
                this.errorHandler = Optional.ofNullable(handler);
            } finally {
                wl.unlock();
            }
            return this;
        }

        @Override
        public BindOptions setWarningHandler(BindExceptionHandler handler) {
            try {
                wl.lock();
                this.warningHandler = Optional.ofNullable(handler);
            } finally {
                wl.unlock();
            }
            return this;
        }

    }

    private class ComponentOptionsImpl implements ComponentOptions {

        /**
         * includes only user-provided steps...still need to do the read from
         * the source data binder
         */
        private final LinkedList<BindingStep> readSteps = new LinkedList<>();
        /**
         * includes on the user-provided steps...still need to do the write to
         * the destination data binder
         */
        private final LinkedList<BindingStep> writeSteps = new LinkedList<>();

        @Override
        public <T> ComponentOptions read(StringDataFormatter<T> formatter) {
            try {
                wl.lock();
                readSteps.add(formatter);
            } finally {
                wl.unlock();
            }
            return this;
        }

        @Override
        public <T> ComponentOptions read(BinaryDataFormatter<T> formatter) {
            try {
                wl.lock();
                readSteps.add(formatter);
            } finally {
                wl.unlock();
            }
            return this;
        }

        @Override
        public <F, T> ComponentOptions read(DataConverter<F, T> converter) {
            try {
                wl.lock();
                readSteps.add(converter);
            } finally {
                wl.unlock();
            }
            return this;
        }

        @Override
        public <T> ComponentOptions write(StringDataFormatter<T> formatter) {
            try {
                wl.lock();
                writeSteps.add(formatter);
            } finally {
                wl.unlock();
            }
            return this;
        }

        @Override
        public <T> ComponentOptions write(BinaryDataFormatter<T> formatter) {
            try {
                wl.lock();
                writeSteps.add(formatter);
            } finally {
                wl.unlock();
            }
            return this;
        }

        @Override
        public <F, T> ComponentOptions write(DataConverter<F, T> converter) {
            try {
                wl.lock();
                writeSteps.add(converter);
            } finally {
                wl.unlock();
            }
            return this;
        }
    }
}
