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

import org.geoint.acetate.functional.ThrowingFunction;
import org.geoint.acetate.model.DomainType;
import org.geoint.acetate.model.InvalidModelException;
import org.geoint.acetate.model.TypeDescriptor;

/**
 *
 * @author steve_siebert
 * @param <T>
 * @param <B>
 */
public abstract class TypeBuilder<T extends DomainType, B extends TypeBuilder>
        extends ModelBuilder<T> {

    final ThrowingFunction<B, DomainBuilder, InvalidModelException> onBuild;
    final TypeDescriptor typeDescriptor;
    String description;

    public TypeBuilder(
            ThrowingFunction<B, DomainBuilder, InvalidModelException> onBuild,
            TypeDescriptor typeDesc) {
        this.onBuild = onBuild;
        this.typeDescriptor = typeDesc;
        this.description = null;
    }
//
//        public TypeBuilder(
//                ThrowingFunction<B, DomainBuilder, InvalidModelException> onBuild,
//                String typeName, String description) {
//            this.onBuild = onBuild;
//            this.typeName = typeName;
//            this.description = description;
//        }

    @Override
    public String getVersion() {
        return typeDescriptor.getVersion();
    }

    @Override
    public String getNamespace() {
        return typeDescriptor.getNamespace();
    }

    @Override
    public String getName() {
        return typeDescriptor.getType();
    }

    public TypeDescriptor getDescriptor() {
        return typeDescriptor;
    }

    public B withDescription(String desc) {
        this.description = (desc == null || desc.isEmpty()) ? null : desc;
        return getBuilder();
    }

    /**
     * Completes the domain type definition, returning the DomainBuilder for
     * which this type is defined.
     *
     * @return domain model builder (not this builder)
     * @throws InvalidModelException if the event is invalid
     */
    public final DomainBuilder complete() throws InvalidModelException {
        return onBuild.apply(getBuilder());
    }

    protected B getBuilder() {
        return (B) this;
    }

}
