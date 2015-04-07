package org.geoint.acetate.data;

/**
 * Generic data parser.
 *
 * @param <S> data source
 * @param <T> resultant java object type from parsing
 */
@FunctionalInterface
public interface DataParser<S, T> {

    /**
     * Parse data from the data source into a java Object.
     *
     * @param source data source
     * @return java object
     * @throws DataParseException thrown if the parser could not parse the data
     */
    T parse(S source) throws DataParseException;

}
