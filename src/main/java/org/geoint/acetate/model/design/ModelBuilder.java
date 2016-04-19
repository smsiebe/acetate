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
import org.geoint.acetate.model.TypeDescriptor;
import org.geoint.acetate.model.resolve.DomainTypeResolver;

/**
 *
 * @author steve_siebert
 * @param <M>
 */
public abstract class ModelBuilder<M> {

    public abstract String getNamespace();

    public abstract String getVersion();

    public abstract String getName();

    /**
     * Creates the domain model component from this builder.
     *
     * @param resolver domain type resolver that may be used to resolveType
     * references
     * @return domain model
     * @throws InvalidModelException if the type definition is not valid
     */
    public abstract M createModel(DomainTypeResolver<TypeDescriptor> resolver)
            throws InvalidModelException;
}
