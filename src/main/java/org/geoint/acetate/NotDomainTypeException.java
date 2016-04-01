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
public class NotDomainTypeException extends InvalidModelException {

    public NotDomainTypeException(Class<?> invalidClass) {
        super(message(invalidClass, null));
    }

    public NotDomainTypeException(Class<?> invalidClass, String message) {
        super(message(invalidClass, message));
    }

    public NotDomainTypeException(Class<?> invalidClass, String message, Throwable cause) {
        super(message(invalidClass, message), cause);
    }

    public NotDomainTypeException(Class<?> invalidClass, Throwable cause) {
        super(message(invalidClass, null), cause);
    }

    public NotDomainTypeException(Method invalidMethod) {
        super(message(invalidMethod, null));
    }

    public NotDomainTypeException(Method invalidMethod, String message) {
        super(message(invalidMethod, message));
    }

    public NotDomainTypeException(Method invalidMethod, String message, Throwable cause) {
        super(message(invalidMethod, message), cause);
    }

    public NotDomainTypeException(Method invalidMethod, Throwable cause) {
        super(message(invalidMethod, null), cause);
    }

    private static String message(Class<?> invalidClass, String msg) {
        StringBuilder sb = new StringBuilder("Class '")
                .append(invalidClass.getName())
                .append("' is not a known or valid domain type.");
        if (msg != null) {
            sb.append(": ")
                    .append(msg);
        }
        return sb.toString();
    }

    private static String message(Method invalidResourceMethod, String msg) {
        StringBuilder sb = new StringBuilder("Method '")
                .append(invalidResourceMethod.toString())
                .append("' is not a known or valid resource operation");
        if (msg != null) {
            sb.append(": ")
                    .append(msg);
        }
        return sb.toString();
    }
}
