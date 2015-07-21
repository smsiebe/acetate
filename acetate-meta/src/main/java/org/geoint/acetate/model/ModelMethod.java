package org.geoint.acetate.model;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import org.geoint.acetate.model.meta.Meta;

/**
 * Method annotated with a model annotation.
 * 
 * @see Meta
 */
public interface ModelMethod extends ModelMember {

    /**
     * Name of the method.
     * 
     * @return  method name
     */
    String getMethodName();
    
    /**
     * 
     * @return method
     */
    Method getMethod();
    
    /**
     * Parameters, in signature order, of the method.
     * 
     * @return method parameters or empty list if no parameters
     */
    List<ModelParameter<?>> getParameters();
    
    /**
     * Return type of the method, or null if the return type is Void.
     * 
     * @return return type or null if void
     */
    ModelTypeUse<?> getReturnType();
    
    /**
     * Exception types of the method signature.
     * 
     * @return exception types of the signature
     */
    Collection<ModelTypeUse<? extends Throwable>> getExceptions();
    
}