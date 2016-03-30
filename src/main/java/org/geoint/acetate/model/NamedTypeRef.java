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

/**
 * A named reference to a domain type.
 *
 * @param <M> referenced domain type
 */
public final class NamedTypeRef<M extends DomainType> extends NamedRef {

    private final M type;

    /**
     * Uses the name of the domain type as the reference name.
     *
     * @param type
     */
    public NamedTypeRef(M type) {
        super(type.getName());
        this.type = type;
    }

    public NamedTypeRef(String name, M type) {
        super(name);
        this.type = type;
    }

    public NamedTypeRef(String name, String description, M type) {
        super(name, description);
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", this.getName(), type.toString());
    }

    /**
     * Referenced domain type model.
     *
     * @return referenced type
     */
    public M getReferencedType() {
        return type;
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

}
