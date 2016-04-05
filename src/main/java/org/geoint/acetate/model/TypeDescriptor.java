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
 * Identity of a specific domain type.
 */
public final class TypeDescriptor {

    private final String namespace;
    private final String version;
    private final String type;

    public TypeDescriptor(String namespace, String version, String type) {
        this.namespace = namespace;
        this.version = version;
        this.type = type;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getVersion() {
        return version;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("%s.%s-%s", namespace, type, version);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.namespace);
        hash = 79 * hash + Objects.hashCode(this.version);
        hash = 79 * hash + Objects.hashCode(this.type);
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
        final TypeDescriptor other = (TypeDescriptor) obj;
        if (!Objects.equals(this.namespace, other.namespace)) {
            return false;
        }
        if (!Objects.equals(this.version, other.version)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return true;
    }

}
