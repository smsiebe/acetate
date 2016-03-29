/*
 * Copyright 2016 geoint.org.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.geoint.acetate.java.model.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.logging.Logger;
import org.geoint.acetate.NotDomainResourceException;
import org.geoint.acetate.java.model.Safe;
import org.geoint.acetate.java.model.EventClass;
import org.geoint.acetate.java.InvalidDomainMethodException;
import org.geoint.acetate.java.model.ResourceClass;
import org.geoint.acetate.java.model.ValueClass;
import org.geoint.acetate.java.model.DomainEvent;
import org.geoint.acetate.model.InvalidModelException;
import org.geoint.acetate.java.model.DomainResource;
import org.geoint.acetate.java.model.Accessor;
import org.geoint.acetate.java.model.DomainValue;
import org.geoint.acetate.java.model.Idempotent;
import org.geoint.acetate.java.model.Operation;
import org.geoint.acetate.java.model.Param;
import org.geoint.acetate.model.ResourceModel;
import org.geoint.acetate.model.EventModel;
import org.geoint.acetate.model.TypeModel;
import org.geoint.acetate.model.NamedTypeRef;
import org.geoint.acetate.model.ValueModel;
import org.geoint.acetate.model.OperationModel;
import org.geoint.acetate.serialization.TypeCodec;
import org.geoint.acetate.java.model.TypeClass;
import org.geoint.acetate.java.model.MapClassRef;

/**
 * Utility class which uses reflection to introspect the java type model for
 * domain model definitions.
 * <p>
 * A domain model may be described using java annotations on constructs meeting
 * domain model concepts.
 *
 * @see DomainResource
 * @see DomainValue
 * @see DomainEvent
 * @see Idempotent
 * @see Safe
 * @see Operation
 * @see Param
 * @see Accessor
 * @author steve_siebert
 */
public class DomainClassReflector {

//    private static final Map<Class, LazyResourceReflector> RESOURCE_CACHE
//            = new ConcurrentHashMap(); //TODO use java.lang.ClassValue?
    private static final Logger LOGGER = Logger.getLogger(DomainClassReflector.class.getName());

    /**
     * Returns a {@link TypeClass} for Uses reflection to create domain type model statically defined by the
     * java class definition.
     * <p>
     * This method first determines the provided class is a collection (or array), 
     * a Map, or a class describing a domain model type through its annotated 
     * definition.  If a collection (or array), this method will return the 
     * model of the target class, not that of the container.  If a Map, this 
     * method will return an instance of {@link KeyValueClass} which describes 
     * both models of the relationship.  Finally, if the provided class does 
     * not statically describe a model, this method will throw a 
     * NotDomainResourceException.
     * 
     * @param <T> class which may describe a domain class
     * @param domainClass class to introspect for a domain model description
     * @return domain type model
     * @throws NotDomainResourceException if class does not describe a domain
     * model through its static definition
     */
    public static <T> ReflectedTypeClass<T> model(Class<T> domainClass)
            throws NotDomainResourceException {

        throw new UnsupportedOperationException();
    }
    
//    /**
//     * Using reflection, introspect a class to determine if it describes a 
//     * domain type model.
//     *
//     * @param <T> java class representing a domain type
//     * @param clazz java class
//     * @return domain type model represented by the class
//     * @throws NotDomainResourceException thrown if the provided class does not 
//     * represent a domain type
//     */
//    private static <T> LazyClassReflector<T> cacheOrCreate(
//            Class<T> clazz) throws NotDomainResourceException {
//        if (clazz.isPrimitive()) {
//            throw new NotDomainResourceException(clazz, "Primitive types may "
//                    + "not be reflected; consider importing acetate java "
//                    + "standard domain.");
//        }
//
//        //do reflection
//        return RESOURCE_CACHE.computeIfAbsent(clazz,
//                LazyResourceReflector::forClass);
//    }



//
//    private static class LazyMapReflector<K, V> implements MapClassRef<K, LazyClassReflector<K>, V, LazyClassReflector<V>> {
//
//        private final LazyTypeClassRef<K, LazyClassReflector<K>> keyRef;
//        private final LazyTypeClassRef<V, LazyClassReflector<V>> valueRef;
//
//        public LazyMapReflector(LazyTypeClassRef<K, LazyClassReflector<K>> keyRef,
//                LazyTypeClassRef<V, LazyClassReflector<V>> valueRef) {
//            this.keyRef = keyRef;
//            this.valueRef = valueRef;
//        }
//
//        @Override
//        public Class<K> getKeyClass() {
//            return keyRef.getReferencedType().getDomainClass();
//        }
//
//        @Override
//        public Class<V> getValueClass() {
//            return valueRef.getReferencedType().getDomainClass();
//        }
//
//        @Override
//        public LazyClassReflector<K> getKeyModel() {
//            return keyRef.getReferencedType();
//        }
//
//        @Override
//        public NamedTypeRef<LazyClassReflector<K>> getKeyRef() {
//            return keyRef;
//        }
//
//        @Override
//        public LazyTypeClassRef<V, LazyClassReflector<V>> getValueRef() {
//            return valueRef;
//        }
//
//        @Override
//        public LazyClassReflector<V> getValueModel() {
//            return valueRef.getReferencedType();
//        }
//
//        @Override
//        public String getDomainNamespace() {
//            return "org.geoint.acetate";
//        }
//
//        @Override
//        public String getDomainType() {
//            return "MapEntry";
//        }
//
//        @Override
//        public String getDomainVersion() {
//            return "1.0";
//        }
//
//        @Override
//        public Optional<String> getDescription() {
//            return Optional.empty();
//        }
//
//    }
//
//    /**
//     *
//     * @param <R> java class representing the referenced type
//     * @param <M> domain model type associated with return type
//     */
//    private static class LazyTypeClassRef<R, M extends LazyClassReflector<R>>
//            implements NamedTypeRef<M> {
//
//        private final String name;
//        private final Optional<String> desc;
//        private final M refModel;
//        private final Method method;
//        private final boolean collection;
//
//        public LazyTypeClassRef(Method method, M refModel, String name,
//                String desc, boolean collection) {
//            this(method, refModel, name, Optional.ofNullable(desc), collection);
//        }
//
//        public LazyTypeClassRef(Method method, M refModel, String name,
//                Optional<String> desc, boolean collection) {
//            this.method = method;
//            this.name = name;
//            this.desc = desc;
//            this.refModel = refModel;
//            this.collection = collection;
//        }
//
//        /**
//         * Create a reference model for a method parameter.
//         *
//         * @param m method
//         * @param p parameter
//         * @return type reference
//         */
//        public static LazyTypeClassRef<?, ?> paramRef(Method m, Parameter p)
//                throws InvalidModelException {
//
//            Param a = p.getAnnotation(Param.class); //may be null
//            Class<?> pClass = p.getType();
//            boolean isCollection = DomainClassReflector.isCollection(pClass);
//            LazyClassReflector<?> paramModel = cacheOrCreate(pClass);
//
//            return new LazyTypeClassRef(m, paramModel,
//                    (a != null && !a.name().isEmpty()) ? a.name() : p.getName(),
//                    (a != null && !a.description().isEmpty()) ? a.description() : null,
//                    isCollection
//            );
//        }
//
//        /**
//         * Create a reference model for the return type of an operation (an
//         * event).
//         *
//         * @param m operation method
//         * @return event reference
//         */
//        public static LazyTypeClassRef<?, LazyEventReflector<?>> eventRef(
//                Method m) throws InvalidModelException {
//
//            Class<?> eClass = m.getReturnType();
//            boolean isCollection = DomainClassReflector.isCollection(eClass);
//            LazyClassReflector<?> eModel = cacheOrCreate(eClass);
//
//            if (!(eModel instanceof LazyEventReflector)) {
//                throw new InvalidDomainMethodException(eModel, eClass, m,
//                        String.format("Method '%s' is not a resource operation, "
//                                + "method does not produce a domain event.",
//                                m.toString()));
//            }
//
//            return new LazyTypeClassRef(m, eModel, eModel.getDomainType(),
//                    eModel.getDescription(), isCollection);
//
//        }
//
//        /**
//         * Create a reference model for the return type of an accessor method.
//         *
//         * @param m accessor method
//         * @return value reference
//         */
//        public static LazyTypeClassRef<?, LazyValueReflector<?>> attributeRef(
//                Method m) throws InvalidModelException {
//
//            if (m.getParameterCount() > 0) {
//                throw new InvalidModelException(
//                        String.format("Method '%s' is not an attribute accessor "
//                                + "method, attribute accessor methods must not "
//                                + "require method parameters.",
//                                m.toString()));
//            }
//
//            Class<?> aClass = m.getReturnType();
//            boolean isCollection = DomainClassReflector.isCollection(aClass);
//            LazyClassReflector<?> aModel = cacheOrCreate(aClass);
//
//            if (!(aModel instanceof LazyValueReflector)) {
//                throw new InvalidDomainMethodException(aModel, aClass, m,
//                        String.format("Method '%s' is not an attribute accessor "
//                                + "method, method does not return a domain value "
//                                + "type.", m.toString()));
//            }
//            
//            Accessor a = m.getAnnotation(Accessor.class);
//            
//            return new LazyTypeClassRef(m, aModel, 
//                    (a != null && !a.name().isEmpty()) ? a.name() : aModel.getDomainType(),
//                    (a != null && !a.description().isEmpty()) ? a.description() : null,
//                    isCollection);
//        }
//
//        /**
//         * Create a reference model for the return type of a linked resource
//         * accessor method.
//         *
//         * @param m linked resource accessor method
//         * @return resource reference
//         */
//        public static LazyTypeClassRef<?, LazyResourceReflector<?>> linkRef(
//                Method m) throws InvalidModelException {
//            
//            if (m.getParameterCount() > 0) {
//                throw new InvalidModelException(
//                        String.format("Method '%s' is not an resource accessor "
//                                + "method, resource accessor methods must not "
//                                + "require method parameters.",
//                                m.toString()));
//            }
//
//            Class<?> aClass = m.getReturnType();
//            boolean isCollection = DomainClassReflector.isCollection(aClass);
//            LazyClassReflector<?> rModel = cacheOrCreate(aClass);
//
//            if (!(rModel instanceof LazyResourceReflector)) {
//                throw new InvalidDomainMethodException(rModel, aClass, m,
//                        String.format("Method '%s' is not an resource accessor "
//                                + "method, method does not return a domain resource "
//                                + "type.", m.toString()));
//            }
//            
//            Accessor a = m.getAnnotation(Accessor.class);
//            
//            return new LazyTypeClassRef(m, rModel, 
//                    (a != null && !a.name().isEmpty()) ? a.name() : rModel.getDomainType(),
//                    (a != null && !a.description().isEmpty()) ? a.description() : null,
//                    isCollection);
//        }
//
//        @Override
//        public String getName() {
//            return name;
//        }
//
//        @Override
//        public Optional<String> getDescription() {
//            return desc;
//        }
//
//        @Override
//        public M getReferencedType() {
//            return refModel;
//        }
//
//        @Override
//        public boolean isCollection() {
//            return collection;
//        }
//
//    }
//
//    private static abstract class DomainMethod<T, R> {
//
//        protected final String name;
//        protected final Optional<String> description;
//        protected final Method method;
//        protected final LazyClassReflector<T> declaringType;
//        protected final LazyClassReflector<R> returnType;
//
//        public DomainMethod(Method method, LazyClassReflector<T> declaringType,
//                LazyClassReflector<R> returnType,
//                String name, String description) {
//            this.declaringType = declaringType;
//            this.name = name;
//            this.description = Optional.ofNullable(
//                    (description == null || description.isEmpty()
//                            ? null
//                            : description));
//            this.method = method;
//            this.returnType = returnType;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public Optional<String> getDescription() {
//            return description;
//        }
//
//        public Method getMethod() {
//            return method;
//        }
//
//        public LazyClassReflector<T> getDeclaringType() {
//            return declaringType;
//        }
//
//        public LazyClassReflector<R> getReturnType() {
//            return returnType;
//        }
//    }
//
//    private static class OperationMethod<T, R>
//            extends DomainMethod<T, R>
//            implements OperationModel {
//
//        private final boolean idempotent;
//        private final boolean safe;
//        private final List<LazyTypeClassRef<?, ?>> parameters;
//        private final LazyEventReflector<R> result;
//
//        private OperationMethod(LazyResourceReflector<T> resource,
//                LazyEventReflector<R> result,
//                Method method, String operationName, String description,
//                List<LazyTypeClassRef<?, ?>> parameters,
//                boolean idempotent,
//                boolean safe) {
//            super(method, resource, result, operationName, description);
//            this.parameters = parameters;
//            this.result = result;
//            this.idempotent = idempotent;
//            this.safe = safe;
//        }
//
//        public static <T> OperationMethod<T, ?> operation(
//                LazyResourceReflector<T> resource,
//                Method m, String name, String description,
//                boolean idempotent, boolean safe)
//                throws InvalidModelException {
//
//            List<LazyTypeClassRef<?, ?>> params = new ArrayList<>();
//            for (Parameter p : m.getParameters()) {
//                params.add(LazyTypeClassRef.paramRef(m, p));
//            }
//
////            List<OperationParameter<T, ?>> param
////                    = Arrays.stream(m.getParameters())
////                    .map((p) -> OperationParameter.forParameter(p, m))
////                    .collect(Collectors.toList());
//            return new OperationMethod(resource,
//                    LazyEventReflector.forClass(m.getReturnType()),
//                    m, name, description, params,
//                    m.isAnnotationPresent(Idempotent.class),
//                    m.isAnnotationPresent(Safe.class));
//        }
//
//        @Override
//        public boolean isIdempotent() {
//            return idempotent;
//        }
//
//        @Override
//        public boolean isSafe() {
//            return safe;
//        }
//
//        @Override
//        public List<LazyTypeClassRef<?, ?>> getParameters() {
//            return parameters;
//        }
//
//        @Override
//        public EventModel getSuccessEventType() {
//            return this.result;
//        }
//
//        @Override
//        public LazyResourceReflector<T> getDeclaringResource() {
//            return (LazyResourceReflector<T>) this.getDeclaringType();
//        }
//
//    }

//    private static class OperationMethodParameter<P, M extends LazyClassReflector<P>>
//            implements NamedTypeRef<M> {
//
//        private final Method method;
//        private final Parameter param;
//        private final String name;
//        private final Optional<String> desc;
//        private final M paramType;
//        private final boolean collection;
//
//        private OperationMethodParameter(M paramType, Method method, Parameter param,
//                String name, String desc, boolean collection) {
//            this.method = method;
//            this.param = param;
//            this.name = name;
//            this.desc = Optional.ofNullable((desc == null || desc.isEmpty()) ? null : desc);
//            this.paramType = paramType;
//            this.collection = collection;
//        }
//
//        /**
//         * Create a parameter type model, ensuring the parameter type is valid
//         * parameter type.
//         *
//         * @param <P> parameter type
//         * @param paramType java class representing domain parameter type
//         * @return parameter metamodel
//         * @throws InvalidModelException if the parameter model is invalid
//         */
//        public static OperationMethodParameter forParameter(Parameter p,
//                Method m) throws InvalidModelException {
//
//            Param a = p.getAnnotation(Param.class); //may be null
//            Class<?> pClass = p.getType();
//            final boolean collection = DomainClassReflector.isCollection(pClass);
//
//            return new OperationMethodParameter(
//                    m, p,
//                    (a != null && !a.name().isEmpty()) ? a.name() : p.getName(),
//                    (a != null) ? a.description() : null,
//                    collection);
//        }
//
//        @Override
//        public String getName() {
//            return name;
//        }
//
//        @Override
//        public Optional<String> getDescription() {
//            return desc;
//        }
//
//        @Override
//        public M getReferencedType() {
//            return paramType;
//        }
//
//        @Override
//        public boolean isCollection() {
//            return collection;
//        }
//
//    }
}
