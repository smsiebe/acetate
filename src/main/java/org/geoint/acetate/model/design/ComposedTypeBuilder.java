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
package org.geoint.acetate.model.design;

import java.util.ArrayList;
import java.util.Collection;
import org.geoint.acetate.functional.ThrowingFunction;
import org.geoint.acetate.model.DomainType;
import org.geoint.acetate.model.DuplicateNamedTypeException;
import org.geoint.acetate.model.InvalidModelException;
import org.geoint.acetate.model.NamedRef;
import org.geoint.acetate.model.TypeDescriptor;
import org.geoint.acetate.model.resolve.DomainTypeResolver;
import org.geoint.acetate.util.KeyedSet;

/**
 *
 * @author steve_siebert
 * @param <T>
 * @param <B>
 */
public abstract class ComposedTypeBuilder<T extends DomainType, B extends TypeBuilder>
            extends TypeBuilder<T, B> {

        final KeyedSet<String, NamedRefBuilder> composites
                = new KeyedSet<>(NamedRefBuilder::getRefName);

        public ComposedTypeBuilder(
                ThrowingFunction<B, DomainBuilder, InvalidModelException> onBuild,
                TypeDescriptor typeDescriptor) {
            super(onBuild, typeDescriptor);
        }

//        public ComposedTypeBuilder(
//                ThrowingFunction<B, DomainBuilder, InvalidModelException> onBuild,
//                 TypeDescriptor typeDescriptor, String description) {
//            super(onBuild, typeDescriptor, description);
//        }
        /**
         * Defines a reference to a (or a collection of) DomainType(s) as a
         * composite.
         *
         * @param refName name for this composite type
         * @param compositeNamespace defaultNamespace of the composite type
         * @param compositeVersion verison of the composite type
         * @param compositeName type name of the composite type
         * @return this builder (fluid interface)
         * @throws InvalidModelException if this definition is invalid
         */
        public NamedTypeRefBuilder<B> withCompositeType(String refName,
                String compositeNamespace, String compositeVersion,
                String compositeName) throws InvalidModelException {

            NamedTypeRefBuilder<B> ref = new NamedTypeRefBuilder(this, refName,
                    compositeNamespace, compositeVersion, compositeName);
            addCompositeRef(ref);
            return ref;
        }

        /**
         * Returns a NamedTypeRefBuilder to define the composite type.
         *
         * @param refName
         * @param localDomainTypeName
         * @return
         * @throws InvalidModelException
         */
        public NamedTypeRefBuilder<B> withCompositeType(String refName,
                String localDomainTypeName) throws InvalidModelException {
            NamedTypeRefBuilder<B> ref = new NamedTypeRefBuilder(this, refName, localDomainTypeName);
            addCompositeRef(ref);
            return ref;
        }

        /**
         * Returns a NamedMapRefBuilder to define the composite map.
         *
         * @param refName
         * @return
         * @throws InvalidModelException
         */
        public NamedMapRefBuilder<B> withCompositeMap(String refName)
                throws InvalidModelException {
            NamedMapRefBuilder<B> ref = new NamedMapRefBuilder(this, refName);
            addCompositeRef(ref);
            return ref;
        }

        public ComposedTypeBuilder<T, B> withComposite(NamedRef compositeRef)
                throws InvalidModelException {
            BuiltNamedRef ref = new BuiltNamedRef(this, compositeRef);
            addCompositeRef(ref);
            return this;
//            if (compositeRef instanceof NamedTypeRef) {
//                return withCompositeType((NamedTypeRef) compositeRef);
//            } else if (compositeRef instanceof NamedMapRef) {
//                return withCompositeMap((NamedMapRef) compositeRef);
//            } else {
//                throw new InvalidModelException(String.format("Unknown "
//                        + "reference type '%s'.",
//                        compositeRef.getClass().getName()));
//            }

        }

        protected void addCompositeRef(NamedRefBuilder ref)
                throws InvalidModelException {
            if (!composites.add(ref)) {
                throw new DuplicateNamedTypeException(ref.getRefName());
            }
        }

        protected Collection<NamedRef> getCompositeRefs(
                DomainTypeResolver<TypeDescriptor> resolver) throws InvalidModelException {
            Collection<NamedRef> refs = new ArrayList<>();
            for (NamedRefBuilder b : this.composites) {
                refs.add(b.createModel(resolver));
            }
            return refs;
        }
}
