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
 * Thrown if there was a problem with a domain.
 *
 * @author steve_siebert
 */
public abstract class InvalidDomainException extends InvalidModelException {

    private final String namespace;
    private final String version;

    public InvalidDomainException(String namespace, String version) {
        this.namespace = namespace;
        this.version = version;
    }

    public InvalidDomainException(String namespace, 
            String version, String message) {
        super(message);
        this.namespace = namespace;
        this.version = version;
    }

    public InvalidDomainException(String namespace, 
            String version, String message, Throwable cause) {
        super(message, cause);
        this.namespace = namespace;
        this.version = version;
    }

    public InvalidDomainException(String namespace, 
            String version, Throwable cause) {
        super(cause);
        this.namespace = namespace;
        this.version = version;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getVersion() {
        return version;
    }

}
