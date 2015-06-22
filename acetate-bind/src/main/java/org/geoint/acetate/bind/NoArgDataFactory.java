package org.geoint.acetate.bind;

import java.lang.reflect.InvocationTargetException;

/**
 * Data factory which constructs an object instance using a no-arg constructor
 * from the context of an annotation.
 *
 * @param <T> java type to create
 */
public final class NoArgDataFactory<T> implements DataFactory<T> {

    /**
     * Creates a java instance using the context-defined types no-argument
     * constructor.
     *
     * @param creationContext creation context
     * @return instance
     * @throws DataBindException thrown if there was a problem creating the
     * instance using the types no-argument constructor
     */
    @Override
    public T getInstance(CreationContext<T> creationContext) throws DataBindException {
        final Class<T> type = creationContext.getType();
        try {
            return type.getConstructor().newInstance();
        } catch (NoSuchMethodException ex) {
            throw new DataBindException(type, "Cannot"
                    + "construct instance of '"
                    + type.getName() + "', a no-arg "
                    + "constructor was not found.  You must either add a no-arg "
                    + "constructor or define a different data factory type "
                    + "for this type.", ex);
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new DataBindException(creationContext.getType(), "Unable to "
                    + "create instance using a no-arg constructor.", ex);
        } catch (SecurityException ex) {
            //TODO add a listing of permissions that must be added to the policy
            throw new DataBindException(type, "Cannot construct instance of '"
                    + type.getName() + "', ensure the application has the "
                    + "appropriate (reflection) permissions.", ex);
        } catch (IllegalArgumentException ex) {
            //we won't get here since we're first, specifically, asking for 
            //the no-arg constructor
            throw new DataBindException(type, "Expected no-arg constructor but"
                    + " newInstance requires parameters", ex);
        } catch (InvocationTargetException ex) {
            throw new DataBindException(type, "Exception was thrown when "
                    + "attempting to construct instance from no-arg "
                    + "constructor", ex);
        }
    }

}
