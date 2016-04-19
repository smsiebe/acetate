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
 * Generic named ref API.
 *
 * @see NamedTypeRefBuilder
 * @see NamedMapRefBuilder
 * @param <B> declaring builder
 * @param <T> type of model ref
 * @author steve_siebert
 */
public abstract class NamedRefBuilder<B extends ModelBuilder, T extends NamedRef>
        extends ModelBuilder<T> {

    protected final B declaringBuilder;
    protected final String refName;
    protected String description;

    public NamedRefBuilder(B declaringBuilder, String refName) {
        this.declaringBuilder = declaringBuilder;
        this.refName = refName;
    }

    @Override
    public String getName() {
        return getRefName();
    }

    String getRefName() {
        return refName;
    }

    @Override
    public String getVersion() {
        return declaringBuilder.getVersion();
    }

    @Override
    public String getNamespace() {
        return declaringBuilder.getNamespace();
    }

    public B complete() {
        return declaringBuilder;
    }

    @Override
    public abstract T createModel(DomainTypeResolver<TypeDescriptor> resolver)
            throws InvalidModelException;
}
