package org.geoint.acetate.data;

/**
 * Converts data (a Java Object) from one type to another.
 *
 * @param <F> data "from" type
 * @param <T> data "to" type
 */
@FunctionalInterface
public interface DataConverter<F, T> {

    /**
     * Convert a Java object from one type to another.
     *
     * @param data source data type
     * @return converted data type
     * @throws DataConversionException thrown if the data conversion could not
     * be completed
     */
    T convert(F data) throws DataConversionException;
}
