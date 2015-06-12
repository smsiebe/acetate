package org.geoint.acetate.model.scan;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geoint.acetate.model.ModelComponent;
import org.geoint.acetate.model.ModelException;

/**
 * Callable ModelScanner wrapper which asynchronously notifies listeners of
 * components from the scanning operation.
 *
 * {@link ModelComponentListener listeners} can only be added to the scanner
 * prior to execution; listeners are automatically unregistered from scanner to
 * prevent memory islands.
 */
public abstract class AsyncModelScanner
        implements Callable<Collection<ModelComponent>>, ModelScanner {

    private final ExecutorService exec;
    private final List<ModelComponentListener> listeners = new ArrayList<>();

    public AsyncModelScanner(ExecutorService exec) {
        this.exec = exec;
    }

    /**
     * Add a listener to the scanner.
     *
     * Only listeners added to the scanner before execution will be notified,
     * otherwise they are ignored.
     *
     * @param listener
     */
    public void listen(ModelComponentListener listener) {
        listeners.add(listener);
    }

    @Override
    public Collection<ModelComponent> call() throws Exception {

        //defensive copy listeners to new collection, preventing 
        //concurrency issues
        final Collection<ModelComponentListener> defensiveListeners
                = new ArrayList<>(listeners);
        final Collection<ModelComponent> components = new ArrayList<>();
        final BlockingQueue<ModelComponent> queue = new LinkedBlockingQueue<>();
        final Future<?> notifier = exec.submit(() -> {
            for (;;) {
                try {
                    ModelComponent c = queue.take();
                    //can't use streams...poor exception handling
                    defensiveListeners.parallelStream()
                            .
                    for (ModelComponentListener l : defensiveListeners) {
                        try {
                            l.validate(c);
                        } catch (ModelComponentRejectedException ex) {

                        } catch (ModelException ex) {

                        }
                    }
                } catch (InterruptedException ex) {
                    if (Thread.interrupted() && exec.isShutdown()) {
                        break;
                    }
                }
            });
        }


    }
