package org.geoint.acetate;

import java.lang.reflect.Method;
import org.geoint.acetate.annotation.Accesses;
import org.geoint.acetate.annotation.Converts;
import org.geoint.acetate.annotation.Mutates;

/**
 * Domain model behavior.
 * <p>
 * A domain model behavior can loosely be thought of like a java method,
 * however, like other domain components it not necessarily tied to a java
 * language construct, such as a object instance {@link Method}.
 *
 * @param <R> java class of the return type
 * @see Mutates
 * @see DomainOperation
 * @see Accesses
 * @see DomainAccessor
 * @see Converts
 * @see DomainConverter
 */
public interface DomainBehavior<R extends DomainType> extends DomainComponent {

    /**
     * Behavior parameters.
     *
     * @return parameter models
     */
    DomainType<?>[] getParameterTypes();

    /**
     * Behavior return model.
     *
     * @return return model
     */
    R getReturnType();

}
