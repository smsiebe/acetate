package org.geoint.metamodel;

import java.lang.reflect.Method;

/**
 * Thrown when an abstract method is attempted to be added to a model.
 */
public class AbstractMethodModelException extends ModelException {

    private final Method method;

    public AbstractMethodModelException(Method method) {
        super(String.format("Method '%s' declared on type '%s' is not a valid "
                + "model method; model methods must not be abstract.",
                method.getName(), method.getDeclaringClass().getName()));
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }

}
