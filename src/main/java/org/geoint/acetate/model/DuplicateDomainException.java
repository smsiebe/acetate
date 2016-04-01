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
 * Thrown if a domain has already been {@link DomainRegistery registered}.
 *
 * @author steve_siebert
 */
public class DuplicateDomainException extends InvalidModelException {

    public DuplicateDomainException(String namespace, String version) {
        super(message(namespace, version));
    }

    public DuplicateDomainException(String namespace, String version,
            Throwable cause) {
        super(message(namespace, version), cause);
    }

    private static String message(String namespace, String version) {
        return String.format("Duplicate domain model '%s-%s'.",
                namespace, version);
    }
}
