package org.geoint.acetate.impl.meta.model;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Fluid API to programmatically create one or more ObjectModel instances.
 *
 * The builder API is thread safe.
 */
public final class ModelBuilder {

    private final Map<Class<?>, WeakReference<ObjectBuilderImpl>> objects
            = new WeakHashMap<>();

    /**
     * Return ObjectModelBuilder to programmatically create an ObjectModel for a
     * java class.
     *
     * @param <T>
     * @param type
     * @return object model builder
     */
    public <T> ObjectModelBuilder<T> forClass(Class<T> type) {
        return (ObjectModelBuilder<T>) registerObjectType(type);
    }

    /**
     * Creates and caches a new ObjectModel builder if one does not already
     * exist for this class.
     *
     * @param objectType
     */
    private ObjectBuilderImpl<?> registerObjectType(Class<?> objectType) {
        synchronized (objects) {
            //register parent class model builder if it hasn't been already
            if (!objects.containsKey(objectType)) {
                ObjectBuilderImpl builder = new ObjectBuilderImpl(objectType);
                objects.put(objectType, new WeakReference(builder));
                return builder;
            }
        }
        return objects.get(objectType).get();
    }

    private class ObjectBuilderImpl<T> implements ObjectModelBuilder<T> {

        private final Class<T> type;
        private final Map<String, String> attributes
                = new ConcurrentHashMap<>();
        private final Map<String, OperationBuilderImpl> operations
                = new HashMap<>();
        private final Set<Class<?>> parents
                = Collections.synchronizedSet(new HashSet<>());
        private final Set<Class<?>> specializations
                = Collections.synchronizedSet(new HashSet<>());

        public ObjectBuilderImpl(Class<T> type) {
            this.type = type;
        }

        @Override
        public ObjectModelBuilder<T> specializes(Class<? super T> parentClass) {
            registerObjectType(parentClass);

            //tell the parent class model builder that this specializes
            objects.get(parentClass).get().specializations.add(type);
            //tell this model builder that it has a parent
            this.parents.add(parentClass);

            return this;
        }

        @Override
        public ObjectModelBuilder<T> withAttribute(String name, String value) {
            this.attributes.put(name, value);
            return this;
        }

        @Override
        public OperationModelBuilder<?> withOperation(String operationName) {
            //return existing operation builder, if exists
            synchronized (operations) {
                if (operations.containsKey(operationName)) {
                    return operations.get(operationName);
                }
                OperationBuilderImpl<?> opBuilder
                        = new OperationBuilderImpl(type, operationName);
                operations.put(operationName, opBuilder);
                return opBuilder;
            }
        }
    }

    private class OperationBuilderImpl<R> implements OperationModelBuilder<R> {

        private final Class<?> contextClass;
        private final String operationName;
        private final Map<String, Class<?>> parameters = new ConcurrentHashMap<>();
        private Class<?> returnType;
        private final Set<Class<? extends Throwable>> exceptions
                = Collections.synchronizedSet(new HashSet<>());

        public OperationBuilderImpl(Class<?> contextClass,
                String operationName) {
            this.contextClass = contextClass;
            this.operationName = operationName;
        }

        @Override
        public OperationModelBuilder<R> withParameter(String param,
                Class<?> model) throws DuplicateParametersException {
            if (parameters.containsKey(param)) {
                if (!parameters.get(param).equals(model)) {
                    throw new DuplicateParametersException(contextClass, param,
                            parameters.get(param), model);
                }
            } else {
                //parameter hasn't previously been registered, ensure it is 
                //registered as an ObjectModel
                registerObjectType(model);
                parameters.put(param, model);
            }
            return this;
        }

        @Override
        public OperationModelBuilder<R> withReturn(Class<?> model) {
            if (returnType != null && returnType.equals(model)) {
                return this;
            }

            //ensure return class type is registered
            registerObjectType(model);
            returnType = model;
            return this;
        }

        @Override
        public OperationModelBuilder<R> voidReturn() {
            returnType = null;
            return this;
        }

        @Override
        public OperationModelBuilder<R> withException(
                Class<? extends Throwable> throwableType) {
            registerObjectType(throwableType);
            exceptions.add(throwableType);
            return this;
        }
    }
}
