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
package org.geoint.acetate;

import java.lang.reflect.Method;
import org.geoint.acetate.model.InvalidModelException;

/**
 * Thrown if a java class/method was attempted to be used as a domain resource,
 * but construct does not represent a domain resource.
 *
 * @author steve_siebert
 */
public class NotDomainResourceException extends InvalidModelException {

    public NotDomainResourceException(Class<?> invalidResourceClass) {
        super(message(invalidResourceClass, null));
    }

    public NotDomainResourceException(Class<?> invalidResourceClass, String message) {
        super(message(invalidResourceClass, message));
    }

    public NotDomainResourceException(Class<?> invalidResourceClass, String message, Throwable cause) {
        super(message(invalidResourceClass, message), cause);
    }

    public NotDomainResourceException(Class<?> invalidResourceClass, Throwable cause) {
        super(message(invalidResourceClass, null), cause);
    }

    public NotDomainResourceException(Method invalidResourceMethod) {
        super(message(invalidResourceMethod, null));
    }

    public NotDomainResourceException(Method invalidResourceMethod, String message) {
        super(message(invalidResourceMethod, message));
    }

    public NotDomainResourceException(Method invalidResourceMethod, String message, Throwable cause) {
        super(message(invalidResourceMethod, message), cause);
    }

    public NotDomainResourceException(Method invalidResourceMethod, Throwable cause) {
        super(message(invalidResourceMethod, null), cause);
    }

    private static String message(Class<?> invalidResourceClass, String msg) {
        StringBuilder sb = new StringBuilder("Class '")
                .append(invalidResourceClass.getName())
                .append("' is not a valid resource class");
        if (msg != null) {
            sb.append(": ")
                    .append(msg);
        }
        return sb.toString();
    }

    private static String message(Method invalidResourceMethod, String msg) {
        StringBuilder sb = new StringBuilder("Method '")
                .append(invalidResourceMethod.toString())
                .append("' is not a valid resource operation");
        if (msg != null) {
            sb.append(": ")
                    .append(msg);
        }
        return sb.toString();
    }
}
