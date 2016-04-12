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
 * Description of a domain "thing".
 *
 * @see ResourceType
 * @see EventType
 * @see ValueType
 * @author steve_siebert
 */
public interface DomainType {

    /**
     * Namespace of the domain.
     *
     * @return domain namespace
     */
    public default String getNamespace() {
        return getTypeDescriptor().getNamespace();
    }

    /**
     * Version of the domain, and therefore the version of the domain type.
     *
     * @return value version
     */
    public default String getVersion() {
        return getTypeDescriptor().getVersion();
    }

    /**
     * Domain-unique name for the type.
     *
     * @return contextual value name
     */
    public default String getName() {
        return getTypeDescriptor().getType();
    }

    /**
     * Check if the domain type descriptor matches this type.
     *
     * @param namespace domain namespace
     * @param version domain version
     * @param typeName type name
     * @return true if this descriptor identifies this domain type
     */
    public default boolean isType(String namespace, String version,
            String typeName) {
        return getTypeDescriptor().describes(namespace, version, typeName);
    }

    public TypeDescriptor getTypeDescriptor();

    /**
     * Optional description for the domain type.
     *
     * @return description
     */
    public Optional<String> getDescription();

}
