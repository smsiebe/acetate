package org.geoint.metamodel;

import java.lang.reflect.Method;

/**
 * Thrown when a element not annotated with the {@link MetaModel} annotation is
 * attempted to be included in modeling.
 */
public class NotMetamodelAnnotatedException extends ModelException {

    private NotMetamodelAnnotatedException(String message) {
        super(message);
    }

    public static NotMetamodelAnnotatedException forMethod(Method m) {
        return new NotMetamodelAnnotatedException(String.format("Method '%s' "
                + "declared on type '%s' is not meta-model annotated.",
                m.getName(), m.getDeclaringClass().getName()));
    }

    public static NotMetamodelAnnotatedException forType(Class<?> t) {
        return new NotMetamodelAnnotatedException(String.format("Type '%s' is "
                + "not meta-model annotated.",
                t.getName()));
    }
}
