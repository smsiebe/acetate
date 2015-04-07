package org.geoint.acetate.bind;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import org.geoint.acetate.Acetate;
import org.geoint.acetate.bind.impl.BindOptionsImpl;
import org.geoint.acetate.bound.impl.BoundDataReader;
import org.geoint.acetate.bound.impl.BoundDataWriter;
import org.geoint.acetate.bind.spi.BindingReader;
import org.geoint.acetate.bind.spi.BindingWriter;
import org.geoint.acetate.bind.spi.DataBinder;
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
    private final BindOptions options;

    /**
     * Create a Binder using default BindOptions.
     */
    public Binder() {
        this(BindOptionsImpl.DEFAULT);
    }

    /**
     * Create a Binder using alternative bind options.
     * @param options 
     */
    public Binder(BindOptions options) {
        this.options = options;
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

}
