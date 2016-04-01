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

/**
 * Thrown when a domain type could not be resolved.
 *
 * @author steve_siebert
 */
public class UnknownTypeException extends InvalidModelException {

    public UnknownTypeException(String namespace, String version, String typeName) {
        super(String.format("Requested domain type '%s.%s-%s' is unknown.",
                namespace, typeName, version));
    }
}