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
import org.geoint.acetate.model.NamedTypeRef;
import org.geoint.acetate.model.TypeDescriptor;
import org.geoint.acetate.model.UnknownTypeException;
import org.geoint.acetate.model.resolve.DomainTypeResolver;
import org.geoint.acetate.model.resolve.UnresolvedException;

/**
 *
 * @author steve_siebert
 */
public class NamedTypeRefBuilder<B extends ModelBuilder>
        extends NamedRefBuilder<B, NamedTypeRef> {

    private final TypeDescriptor refTypeDescriptor;
    private boolean collection;

    public NamedTypeRefBuilder(B declaringBuilder, String refName,
            String refNamespace, String refVersion, String refType) {
        this(declaringBuilder, refName,
                new TypeDescriptor(refNamespace, refVersion, refType));
    }

    public NamedTypeRefBuilder(B declaringBuilder,
            String refName, String refType) {
        this(declaringBuilder, refName,
                new TypeDescriptor(declaringBuilder.getNamespace(),
                        declaringBuilder.getVersion(),
                        refType));
    }

    public NamedTypeRefBuilder(B declaringBuilder, String refName,
            TypeDescriptor td) {
        super(declaringBuilder, refName);
        this.refTypeDescriptor = td;
    }

    public NamedTypeRefBuilder(B builder, NamedTypeRef ref) {
        super(builder, ref.getName());
        this.refTypeDescriptor
                = new TypeDescriptor(ref.getReferencedType().getNamespace(),
                        ref.getReferencedType().getVersion(),
                        ref.getReferencedType().getName());
        this.description = ref.getDescription().orElse(null);
        this.collection = ref.isCollection();
    }

    public NamedTypeRefBuilder<B> withDescription(String desc) {
        this.description = (desc == null || desc.isEmpty()) ? null : desc;
        return this;
    }

    public NamedTypeRefBuilder<B> isCollection(boolean collection) {
        this.collection = collection;
        return this;
    }

    @Override
    public NamedTypeRef createModel(DomainTypeResolver<TypeDescriptor> resolver)
            throws InvalidModelException {
        try {
            return new NamedTypeRef(resolver.resolve(refTypeDescriptor),
                    refName,
                    description,
                    collection);
        } catch (UnresolvedException ex) {
            throw new UnknownTypeException(refTypeDescriptor, ex);
        }
    }

}
