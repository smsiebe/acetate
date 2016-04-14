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
package org.geoint.acetate;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.geoint.acetate.functional.SingletonSupplier;
import org.geoint.acetate.model.DomainType;
import org.geoint.acetate.model.InvalidModelException;
import org.geoint.acetate.model.NamedTypeRef;

/**
 *
 */
public abstract class TypeInstanceRef implements InstanceRef<NamedTypeRef> {

    private final NamedTypeRef ref;

    protected TypeInstanceRef(NamedTypeRef ref) {
        this.ref = ref;
    }

    /**
     * Create a reference by binding to one or more type instances.
     * <p>
     * The created reference will bind to the instances at construction time.
     *
     * @param ref reference model
     * @param instances type instances
     * @return reference
     * @throws InvalidModelException if the bound instances do not match the
     * reference model
     */
    public TypeInstanceRef createInstance(NamedTypeRef ref, TypeInstance... instances)
            throws InvalidModelException {
        for (TypeInstance i : instances) {
            if (!i.getTypeDescriptor().describes(ref.getReferencedType())) {
                throw new InvalidModelException(String.format("Instance type "
                        + "'%s' cannot be referenced by '%s'",
                        i.toString(), this.toString()));
            }
        }
        return new ArrayBackedInstanceRef(ref, instances);
    }

    /**
     * Defers instance retrieval until explicitly called, allowing
     * implementations to lazy-load, late-bind, or whatever crazy kids do these
     * days.
     *
     * @param ref reference model
     * @param instances supplier used to retrieve the instances referenced
     * @return new deferred type instance ref
     */
    public static TypeInstanceRef newInstance(NamedTypeRef ref,
            Supplier<TypeInstance[]> instances) {
        return new DeferredTypeInstanceRef(ref, instances);
    }

    @Override
    public NamedTypeRef getRefModel() {
        return ref;
    }

    public DomainType getTypeModel() {
        return ref.getReferencedType();
    }

    /**
     * Indicates if the type reference supports multiple instances of the value
     * or just one.
     *
     * @return true if multiple instances are supported, otherwise false
     */
    public boolean isCollection() {
        return ref.isCollection();
    }

    /**
     * Consume zero or more type instances.
     * <p>
     * If not referenced instances are defined by the domain instance, the
     * provided consumer will not be called.
     *
     * @param consumer called for each referenced instance
     */
    public abstract void forEachType(Consumer<TypeInstance> consumer);

    private static final class DeferredTypeInstanceRef extends TypeInstanceRef {

        private final SingletonSupplier<TypeInstance[]> instance;

        /**
         *
         * @param refModel
         * @param instanceSupplier
         */
        private DeferredTypeInstanceRef(NamedTypeRef refModel,
                Supplier<TypeInstance[]> refSupplier) {
            super(refModel);
            this.instance = SingletonSupplier.newSingleton(refSupplier);
        }

        @Override
        public void forEachType(Consumer<TypeInstance> consumer) {
            Arrays.stream(instance.get()).forEach(consumer);
        }

    }

    private static class ArrayBackedInstanceRef extends TypeInstanceRef {

        private final TypeInstance[] instance;

        private ArrayBackedInstanceRef(NamedTypeRef refModel,
                TypeInstance... instance) {
            super(refModel);
            this.instance = instance;
        }

        @Override
        public void forEachType(Consumer<TypeInstance> consumer) {
            for (TypeInstance i : instance) {
                consumer.accept(i);
            }
        }
    }
//      /**
//     * Late-binding type instance reference which defers until instances are
//     * attempted to be retrieved.
//     */
//    private static class DeferredTypeInstanceRef implements TypeInstanceRef {
//
//        private final NamedTypeRef refModel;
//        private Supplier<Spliterator<TypeInstance>> spliterator;
//        private TypeInstance[] instances;
//
//        public DeferredTypeInstanceRef(NamedTypeRef refModel,
//                Supplier<Spliterator<TypeInstance>> spliterator) {
//            this.refModel = refModel;
//            this.spliterator = spliterator;
//        }
//
//        @Override
//        public void forEachType(Consumer<TypeInstance> consumer) {
//
//            synchronized (this) {
//                if (instances == null) {
//                    instances = StreamSupport.stream(spliterator.get(), false)
//                            .toArray((i) -> new TypeInstance[i]);
//                    spliterator = null;
//                }
//            }
//
//            for (TypeInstance i : instances) {
//                consumer.accept(i);
//            }
//        }
//
//        @Override
//        public NamedTypeRef getRefModel() {
//            return refModel;
//        }
//
//    }
}
