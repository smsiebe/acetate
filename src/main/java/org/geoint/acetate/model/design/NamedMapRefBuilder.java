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
import org.geoint.acetate.model.NamedMapRef;
import org.geoint.acetate.model.NamedRef;
import org.geoint.acetate.model.NamedTypeRef;
import org.geoint.acetate.model.TypeDescriptor;
import org.geoint.acetate.model.resolve.DomainTypeResolver;

/**
 *
 * @author steve_siebert
 */
public class NamedMapRefBuilder<B extends ModelBuilder>
        extends NamedRefBuilder<B, NamedMapRef> {

    private NamedTypeRefBuilder<NamedMapRefBuilder<B>> keyRefBuilder;
    private NamedRefBuilder<NamedMapRefBuilder<B>, ?> valueRefBuilder;

    public NamedMapRefBuilder(B declaringBuilder, String refName) {
        super(declaringBuilder, refName);
    }

    public NamedMapRefBuilder(B declaringBuilder, String refName,
            NamedTypeRefBuilder<NamedMapRefBuilder<B>> keyRefBuilder,
            NamedRefBuilder<NamedMapRefBuilder<B>, ?> valueRefBuilder) {
        super(declaringBuilder, refName);
        this.keyRefBuilder = keyRefBuilder;
        this.valueRefBuilder = valueRefBuilder;
    }

    public NamedMapRefBuilder(B builder, NamedMapRef ref)
            throws InvalidModelException {
        super(builder, ref.getName());
        this.keyRefBuilder = new NamedTypeRefBuilder(builder, ref.getKeyRef());
        if (ref.getValueRef() instanceof NamedTypeRef) {
            this.valueRefBuilder = new NamedTypeRefBuilder(builder, (NamedTypeRef) ref.getValueRef());
        } else if (ref.getValueRef() instanceof NamedMapRef) {
            this.valueRefBuilder = new NamedMapRefBuilder(builder, (NamedMapRef) ref.getValueRef());
        } else {
            throw new InvalidModelException(String.format("Unknown map "
                    + "value model reference type '%s'",
                    ref.getValueRef().getClass().getName()));
        }
    }

    public NamedMapRefBuilder<B> withDescription(String desc) {
        this.description = desc;
        return this;
    }

    public NamedTypeRefBuilder<NamedMapRefBuilder<B>> keyType(String refName,
            TypeDescriptor td) {
        this.keyRefBuilder = new NamedTypeRefBuilder(this, refName, td);
        return this.keyRefBuilder;
    }

    /**
     * Specify the key type as a domain type of the domain being built.
     *
     * @param typeName domain type name
     * @return this builder
     */
    public NamedTypeRefBuilder<NamedMapRefBuilder<B>> keyType(String typeName) {
        this.keyRefBuilder = new NamedTypeRefBuilder(this, typeName, typeName);
        return this.keyRefBuilder;
    }

    public NamedTypeRefBuilder<NamedMapRefBuilder<B>> keyType(String refName,
            String typeName) {
        this.keyRefBuilder = new NamedTypeRefBuilder(this, refName, typeName);
        return this.keyRefBuilder;
    }

    public NamedTypeRefBuilder<NamedMapRefBuilder<B>> keyType(String refName,
            String refNamespace, String refVersion, String refType) {
        this.keyRefBuilder = new NamedTypeRefBuilder(this, refName, refNamespace, refVersion, refType);
        return this.keyRefBuilder;
    }

    public NamedMapRefBuilder<B> keyRef(NamedTypeRefBuilder ref) {
        this.keyRefBuilder = ref;
        return this;
    }

    public NamedTypeRefBuilder<NamedMapRefBuilder<B>> valueType(String typeName) {
        this.valueRefBuilder = new NamedTypeRefBuilder(this, typeName, typeName);
        return (NamedTypeRefBuilder<NamedMapRefBuilder<B>>) this.valueRefBuilder;
    }

    public NamedTypeRefBuilder<NamedMapRefBuilder<B>> valueType(String refName,
            String typeName) {
        this.valueRefBuilder = new NamedTypeRefBuilder(this, refName, typeName);
        return (NamedTypeRefBuilder<NamedMapRefBuilder<B>>) this.valueRefBuilder;
    }

    public NamedTypeRefBuilder<NamedMapRefBuilder<B>> valueType(String refName,
            TypeDescriptor td) {
        this.valueRefBuilder = new NamedTypeRefBuilder(this, refName, td);
        return (NamedTypeRefBuilder<NamedMapRefBuilder<B>>) this.valueRefBuilder;
    }

    public NamedTypeRefBuilder<NamedMapRefBuilder<B>> valueType(String refName,
            String refNamespace, String refVersion, String refType) {
        this.valueRefBuilder = new NamedTypeRefBuilder(this, refName,
                refNamespace, refVersion, refType);
        return (NamedTypeRefBuilder<NamedMapRefBuilder<B>>) this.valueRefBuilder;
    }

    public NamedMapRefBuilder<NamedMapRefBuilder<B>> valueMap(String refName) {
        this.valueRefBuilder = new NamedMapRefBuilder(this, refName);
        return (NamedMapRefBuilder<NamedMapRefBuilder<B>>) this.valueRefBuilder;
    }

    public NamedMapRefBuilder<B> valueRef(NamedRefBuilder ref) {
        this.valueRefBuilder = ref;
        return this;
    }

    @Override
    public NamedMapRef createModel(DomainTypeResolver<TypeDescriptor> resolver)
            throws InvalidModelException {
        NamedTypeRef keyRef = keyRefBuilder.createModel(resolver);
        NamedRef valueRef = valueRefBuilder.createModel(resolver);
        return new NamedMapRef(this.refName,
                this.description, keyRef, valueRef);
    }
}
