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
import java.util.Optional;

/**
 * Description of a domain "thing".
 *
 * @see ResourceType
 * @see EventType
 * @see ValueType
 * @author steve_siebert
 */
public abstract class DomainType {

    protected final TypeDescriptor descriptor;
    private final Optional<String> description;

    public DomainType(String namespace, String version, String name) {
        this(namespace, version, name, null);
    }

    public DomainType(String namespace, String version, String name,
            String description) {
        this.descriptor = new TypeDescriptor(namespace, version, name);
        this.description = Optional.ofNullable(description);
    }

    /**
     * Namespace of the domain.
     *
     * @return domain namespace
     */
    public String getNamespace() {
        return descriptor.getNamespace();
    }

    /**
     * Version of the domain, and therefore the version of the domain type.
     *
     * @return value version
     */
    public String getVersion() {
        return descriptor.getVersion();
    }

    /**
     * Domain-unique name for the type.
     *
     * @return contextual value name
     */
    public String getName() {
        return descriptor.getType();
    }

    public TypeDescriptor getTypeDescriptor() {
        return descriptor;
    }

    /**
     * Check if the domain type descriptor matches this type.
     *
     * @param namespace domain namespace
     * @param version domain version
     * @param typeName type name
     * @return true if this descriptor identifies this domain type
     */
    public boolean isType(String namespace, String version, String typeName) {
        return this.descriptor.describes(namespace, version, typeName);
    }

    /**
     * Optional description for the domain type.
     *
     * @return description
     */
    public Optional<String> getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return descriptor.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.descriptor);
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
        final DomainType other = (DomainType) obj;
        return Objects.equals(this.descriptor, other.descriptor);
    }

}
