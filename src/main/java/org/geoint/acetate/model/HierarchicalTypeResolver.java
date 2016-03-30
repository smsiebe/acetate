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

import java.util.Optional;

/**
 * Decorates a TypeResolver creating a type resolution hierarchy.
 * <p>
 * Each HierarchicalTypeResolver may have one parent and the resolution of a
 * domain type is up the hierarchy until a type is found.
 *
 * @author steve_siebert
 */
public class HierarchicalTypeResolver implements TypeResolver {

    private final HierarchicalTypeResolver parent; //null indicates the root/parent node
    private final TypeResolver resolver;

    private HierarchicalTypeResolver(TypeResolver resolver) {
        this.parent = null;
        this.resolver = resolver;
    }

    private HierarchicalTypeResolver(HierarchicalTypeResolver parent, TypeResolver resolver) {
        this.parent = parent;
        this.resolver = resolver;
    }

    public static HierarchicalTypeResolver newHierarchy(TypeResolver resolver) {
        return new HierarchicalTypeResolver(resolver);
    }

    public HierarchicalTypeResolver addChild(TypeResolver resolver) {
        return new HierarchicalTypeResolver(this);
    }

    @Override
    public Optional<DomainType> findType(String namespace,
            String version, String typeName) {
        Optional<DomainType> type
                = this.resolver.findType(namespace, version, typeName);
        if (!type.isPresent() && this.parent != null) {
            return this.parent.findType(namespace, version, typeName);
        }
        return type;
    }

}
