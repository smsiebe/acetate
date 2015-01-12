package org.geoint.acetate.codec;

/**
 * Base codec definition.
 *
 * @param <F> the java object "from" which to create the alternative data
 * representation
 * @param <T> the alternative data representation "to" which the codec converts
 */
public interface AcetateCodec<F, T> {

    /**
     * Converts a java object to an alternative representation.
     *
     * Note this method creates the alternative representation in memory. It's
     * recommended to use the {@link StreamCodec} interface if possible.
     *
     * @param dataItem java object type
     * @return the alternative format
     * @throws AcetateTransformException thrown if there is a conversion problem
     */
    T convert(F dataItem) throws AcetateTransformException;

    /**
     * Inverts an alternative data item representation to a java object.
     *
     * Note this method requires that the alternative representation is stored
     * in memory. It's recommended to use the {@link StreamCodec} interface if
     * possible.
     *
     * @param acetate alternative data item representation
     * @return the java object
     * @throws AcetateTransformException thrown if there is a conversion problem
     */
    F invert(T acetate) throws AcetateTransformException;
}
