package org.geoint.acetate.data.transform;

/**
 * Transforms character data from one string form to another.
 */
@FunctionalInterface
public interface StringFormatter {

    /**
     * Formats character data from one string form to another.
     *
     * @param dataString
     * @return
     * @throws DataConversionException
     */
    String format(String dataString) throws DataConversionException;
}
