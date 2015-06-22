package org.geoint.acetate.model.reflect;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.geoint.acetate.meta.MetaVersion;
import org.geoint.acetate.meta.model.ObjectModel;
import org.geoint.acetate.meta.model.OperationModel;

/**
 * Uses reflection to create object model.
 *
 */
public class ReflectionModeler {

    private static final Map<Class<?>, WeakReference<ObjectModel<?>>> modelCache
            = new WeakHashMap<>();
    private static final ExecutorService executor
            = Executors.newCachedThreadPool();

    /**
     * Model the provided class using reflection.
     *
     * @param <T>
     * @param toModel
     * @return object meta model or null if class is not defined as a model
     */
    public static <T> ObjectModel<T> model(Class<T> toModel) {
        synchronized (modelCache) {
            if (!modelCache.containsKey(toModel)) {
                modelCache.put(toModel, new WeakReference(
                        ObjectModelReflector.reflect(toModel)));
            }
        }
        return (ObjectModel<T>) modelCache.get(toModel).get();
    }

    private static class ObjectModelReflector<T> implements ObjectModel<T>, Runnable {

        private volatile Future<?> running;
        private final Class<T> type;
        
        //asynchronously set by thread
        private MetaVersion version;
        private Collection<OperationModel> operations;
        private Collection<ObjectModel<? super T>> parents;
        private Collection<ObjectModel<? extends T>> specialized;
        
        public ObjectModelReflector(Class<T> type) {
            this.type = type;
        }

        private static <T> ObjectModel<T> reflect(
                Class<T> toModel) {
            ObjectModelReflector<T> model = new ObjectModelReflector(toModel);
            model.running = executor.submit(model);
            return model;
        }

        @Override
        public void run () {
            
        }
        
        @Override
        public Class<T> getObjectType() {
            return type;
        }

        @Override
        public MetaVersion getVersion() {

        }

        @Override
        public Collection<OperationModel> getOperations() {

        }

        @Override
        public Collection<ObjectModel<? super T>> getParents() {

        }

        @Override
        public Collection<ObjectModel<? extends T>> getSpecialized() {

        }

        private <T> whenComplete (Function<T> asyncFunction) {
            
        }
    }
}
