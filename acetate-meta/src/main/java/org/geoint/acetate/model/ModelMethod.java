package org.geoint.acetate.model;

import java.lang.reflect.Method;
import org.geoint.acetate.model.meta.Meta;
import org.geoint.acetate.model.provider.Resolver;

/**
 * Method annotated with a model annotation.
 *
 * @see Meta
 */
public class ModelMethod extends ModelMember<Method> implements ModelTypeUse {

    private final ModelParameter<?>[] params;
    private final ModelTypeUse<? extends Throwable>[] exceptions;
    private final ModelTypeUse<?> returnType;

    public ModelMethod(ModelType declaringType,
            String name,
            ModelAnnotation<?>[] useAnnotations,
            ModelParameter<?>[] params,
            ModelTypeUse<? extends Throwable>[] exceptions,
            ModelTypeUse<?> returnType,
            Resolver<Method> elementResolver) {
        super(declaringType, name, useAnnotations, elementResolver);
        this.params = params;
        this.exceptions = exceptions;
        this.returnType = returnType;
    }

    public ModelMethod(ModelType declaringType,
            String name,
            ModelAnnotation<?>[] useAnnotations,
            ModelParameter<?>[] params,
            ModelTypeUse<? extends Throwable>[] exceptions,
            ModelTypeUse<?> returnType,
            Method method) {
        super(declaringType, name, useAnnotations, method);
        this.params = params;
        this.exceptions = exceptions;
        this.returnType = returnType;
    }

    /**
     *
     * @return method
     */
    public Method getMethod() {
        return resolver.resolve();
    }

    /**
     * Parameters, in signature order, of the method.
     *
     * @return method parameters in method signature declaration order, or empty
     * array if no parameters
     */
    public ModelParameter<?>[] getParameters() {
        return params;
    }

    /**
     * Return type of the method, or null if the return type is Void.
     *
     * @return return type or null if void
     */
    public ModelTypeUse<?> getReturnType() {
        return returnType;
    }

    /**
     * Exception types of the method signature.
     *
     * @return exception types of the signature or empty array if no exceptions
     * are declared
     */
    public ModelTypeUse<? extends Throwable>[] getExceptions() {
        return exceptions;
    }

    @Override
    public int getModifiers() {
        return getMethod().getModifiers();
    }

    @Override
    public boolean isSynthetic() {
        return getMethod().isSynthetic();
    }

    @Override
    public void visit(ModelVisitor visitor) {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public ModelAnnotation[] getUseModelAnnotations() {
        return this.modelAnnotations;
    }

}
