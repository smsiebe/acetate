package org.geoint.acetate.transform;

/**
 * Formats a data type from one format to another without changing it's "type".
 *
 * @param <T> data type
 * @see DataConverter
 */
@FunctionalInterface
public interface DataFormatter<T> extends DataTransformer  {

    /**
     * Change the data format (visualization).
     *
     * @param previousFormat
     * @return reformatted data
     * @throws DataFormatException thrown if there is a problem formatting the
     * data
     */
    T format(T previousFormat) throws DataFormatException;

}
