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

import org.geoint.acetate.model.InvalidModelException;
import org.geoint.acetate.model.NamedRef;
import org.geoint.acetate.model.TypeDescriptor;
import org.geoint.acetate.model.resolve.DomainTypeResolver;

/**
 * NamedRef builder used when the reference has already been built.
 *
 * @param <T>
 * @param <B>
 * @author steve_siebert
 */
public class BuiltNamedRef<T extends NamedRef, B extends ModelBuilder>
        extends NamedRefBuilder<B, T> {

    private final T ref;

    public BuiltNamedRef(B declaringBuilder, T ref) {
        super(declaringBuilder, ref.getName());
        this.ref = ref;
    }

    @Override
    public T createModel(DomainTypeResolver<TypeDescriptor> resolver)
            throws InvalidModelException {
        return ref;
    }

    @Override
    public String getNamespace() {
        return this.declaringBuilder.getNamespace();
    }

    @Override
    public String getVersion() {
        return this.declaringBuilder.getVersion();
    }

}
