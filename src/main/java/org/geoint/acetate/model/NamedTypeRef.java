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
package org.geoint.acetate.model;

import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.geoint.acetate.TypeInstance;
import org.geoint.acetate.TypeInstanceRef;

/**
 * A named reference to a domain type.
 *
 * @param <M> referenced domain type
 */
public final class NamedTypeRef<M extends DomainType> extends NamedRef {

    private final M type;
    private final boolean collection;

    public NamedTypeRef(M type, String refName, String description, boolean isCollection) {
        super(refName, description);
        this.type = type;
        this.collection = isCollection;
    }

    /**
     * Uses the name of the domain type as the reference name.
     *
     * @param type
     */
    public NamedTypeRef(M type) {
        this(type, type.getName(), null, false);
    }

    public NamedTypeRef(M type, String name) {
        this(type, name, null, false);
    }

    public NamedTypeRef(M type, String name, String description) {
        this(type, name, description, false);
    }

    public boolean isCollection() {
        return collection;
    }

    /**
     * Referenced domain type model.
     *
     * @return referenced type
     */
    public M getReferencedType() {
        return type;
    }

    /**
     * Create a reference by binding to one or more type instances.
     * <p>
     * The created reference will bind to the instances at construction time.
     *
     * @param instances type instances
     * @return reference
     * @throws InvalidModelException if the bound instances do not match the
     * reference model
     */
    public TypeInstanceRef createInstance(TypeInstance... instances)
            throws InvalidModelException {
        for (TypeInstance i : instances) {
            if (!i.getTypeDescriptor().describes(getReferencedType())) {
                throw new InvalidModelException(String.format("Instance type "
                        + "'%s' cannot be referenced by '%s'",
                        i.toString(), this.toString()));
            }
        }
        return new DefaultTypeInstanceRef(this, instances);
    }

    /**
     * Creates a <i>late-binding</i> reference that retrieves the referenced
     * instances on first request rather than at construction time.
     *
     * @param instances called at most once to retrieve referenced instances
     * @return late-binding type reference
     */
    public TypeInstanceRef createDeferredInstance(Supplier<Spliterator<TypeInstance>> instances) {
        return new DeferredTypeInstanceRef(this, instances);
    }

    @Override
    public String toString() {
        return String.format("%s: %s", this.getName(), type.toString());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.getName());
        hash = 61 * hash + Objects.hashCode(this.type);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NamedTypeRef<?> other = (NamedTypeRef<?>) obj;
        if (!Objects.equals(this.getName(), other.getName())) {
            return false;
        }
        return Objects.equals(this.type, other.type);
    }

    /**
     * Late-binding type instance reference which defers until instances are
     * attempted to be retrieved.
     */
    private static class DeferredTypeInstanceRef implements TypeInstanceRef {

        private final NamedTypeRef refModel;
        private Supplier<Spliterator<TypeInstance>> spliterator;
        private TypeInstance[] instances;

        public DeferredTypeInstanceRef(NamedTypeRef refModel,
                Supplier<Spliterator<TypeInstance>> spliterator) {
            this.refModel = refModel;
            this.spliterator = spliterator;
        }

        @Override
        public void forEachType(Consumer<TypeInstance> consumer) {

            synchronized (this) {
                if (instances == null) {
                    instances = StreamSupport.stream(spliterator.get(), false)
                            .toArray((i) -> new TypeInstance[i]);
                    spliterator = null;
                }
            }

            for (TypeInstance i : instances) {
                consumer.accept(i);
            }
        }

        @Override
        public NamedTypeRef getRefModel() {
            return refModel;
        }

    }

    private static class DefaultTypeInstanceRef implements TypeInstanceRef {

        private final NamedTypeRef refModel;
        private final TypeInstance[] instance;

        public DefaultTypeInstanceRef(NamedTypeRef refModel,
                TypeInstance... instance) {
            this.refModel = refModel;
            this.instance = instance;
        }

        @Override
        public void forEachType(Consumer<TypeInstance> consumer) {
            for (TypeInstance i : instance) {
                consumer.accept(i);
            }
        }

        @Override
        public NamedTypeRef getRefModel() {
            return refModel;
        }

    }
}
