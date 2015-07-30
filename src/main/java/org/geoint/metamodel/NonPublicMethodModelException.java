package org.geoint.metamodel;

import java.lang.reflect.Method;

/**
 * Thrown if a method annotated with a model annotation is not public.
 */
public class NonPublicMethodModelException extends ModelException {

    private final Method method;

    public NonPublicMethodModelException(Method method) {
        super(String.format("Method '%s' declared on type '%s' is not a valid"
                + "model method; model methods must have public visibility.",
                method.getName(), method.getDeclaringClass().getName()));
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }

}
