package gov.ic.geoint.acetate.bind;

/**
 * Defines the context to create the object instance.
 *
 * @param <T> object type to create
 */
public interface CreationContext<T> {

    Class<T> getType();

}
