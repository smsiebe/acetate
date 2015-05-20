package gov.ic.geoint.acetate.bind;

/**
 * Composes the respective java object.
 *
 * @param <T> java type that is created from context
 * @param <C> creation context used to generate the instance
 */
@FunctionalInterface
public interface DataFactory<T, C extends CreationContext<T>> {

    /**
     * Returns an instance based on the creation context.
     *
     * @param creationContext creation context
     * @return java object representing the data type/value.
     * @throws DataBindException thrown if there are any problems instantiating
     * the data
     */
    T getInstance(C creationContext) throws DataBindException;

}
