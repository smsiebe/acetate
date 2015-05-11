package org.geoint.acetate.impl.model.cache;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.ComponentModel;
import org.geoint.acetate.model.constraint.ComponentConstraint;
import gov.ic.geoint.acetate.bind.DataType;

/**
 * WeakHashMap backed cache which provides support for asynchronous component
 * creation.
 *
 */
public class AsyncWeakTypedModelCache implements TypedModelCache {

    private static final Map<Class<?>, ComponentModel<?>> cache
            = Collections.synchronizedMap(new WeakHashMap());
    private static final Logger logger
            = Logger.getLogger(AsyncWeakTypedModelCache.class.getName());

    @Override
    public <T> ComponentModel<T> getOrCache(Class<T> type,
            Supplier<Callable<ComponentModel<T>>> creator) {
        synchronized (cache) {
            if (!cache.containsKey(type)) {
                //immediatley add to cache so we can get out of this terrible
                //sync block as soon as possible
                cache.put(type, new DataComponentPromise(type, creator.get()));
            }
        }
        return (ComponentModel<T>) cache.get(type);
    }

    @Override
    public <T> ComponentModel<T> put(Class<T> type, ComponentModel<T> component) {
        return (ComponentModel<T>) cache.put(type, component);
    }

    /**
     * Wraps the asynchronous operation of creating the data component so we're
     * not having to juggle futures, etc etc.
     *
     * This object simply remains serves as a delegating proxy until the
     * execution is complete, then it replaces itself with the actual value.
     *
     * @param <T>
     */
    private class DataComponentPromise<T> implements ComponentModel<T> {

        private final Class<T> clazz; //also used as synchronizor for promise
        private volatile boolean running;
        private ComponentModel results;
        private final long WAIT_TIMEOUT = TimeUnit.MINUTES.toMicros(1);

        public DataComponentPromise(final Class<T> clazz,
                final Callable<ComponentModel<T>> creator) {
            this.clazz = clazz;

            //TODO make use of resource management, if available
            Thread t = new Thread(() -> {
                try {
                    results = creator.call();
                } catch (Exception ex) {
                    //rethrow as runtime exception, will be caught, logged,
                    //and unregistered by the UncaughtExceptionHandler below
                    throw new RuntimeException(ex);
                }
            });
            t.setDaemon(true);
            t.setUncaughtExceptionHandler((thread, ex) -> {

                logger.log(Level.SEVERE, "Asynchronous data component "
                        + "promise could not complete, data component "
                        + "creator unexpectidly threw an exception.", ex);

                //unregister from the cache
                cache.remove(clazz);

                //flag as no longer running
                running = false;

                //notify threads waiting
                synchronized (this.clazz) {
                    this.clazz.notifyAll();
                }
            });
            t.start();
        }

        @Override
        public DataType<T> getType() {
            return waitForResults(WAIT_TIMEOUT, results::getType);
        }

        @Override
        public Collection<ComponentConstraint<T>> getConstraints() {
            return waitForResults(WAIT_TIMEOUT, results::getConstraints);
        }

        @Override
        public Collection<ComponentAttribute> getAttributes() {
            return waitForResults(WAIT_TIMEOUT, results::getAttributes);
        }

        private <T> T waitForResults(long timeout, Supplier<T> delegate) {
            if (!running) {
                if (results != null) {
                    //results are avalable!
                    delegate.get();
                } else { //not running, no results
                    //we'll get here on any thread that was waiting for the 
                    //aysnc creation to complete, but the creation died with 
                    //an exception, and the threads were notified
                    throw new RuntimeException("Asynchronous model component "
                            + "creation failed.");
                }
            }

            //it's currently running...so lets wait
            final long waitStarted = System.currentTimeMillis();
            synchronized (this.clazz) {
                try {
                    this.clazz.wait(timeout);
                } catch (InterruptedException ex) {
                    //shhh
                    Thread.interrupted();
                }
            }

            //if the results aren't ready and we haven't hit 
            //the timeout, continue to wait
            if (running) {
                final long remainingWait = (waitStarted + timeout)
                        - System.currentTimeMillis();
                if (remainingWait < 0) {
                    throw new RuntimeException("Asynchronous model "
                            + "component creation took longer "
                            + "than exepected, creation failed.");
                }
                return waitForResults(remainingWait, delegate);
            }

            //return results through the exception check 
            return waitForResults(0, delegate);
        }

    }

}
