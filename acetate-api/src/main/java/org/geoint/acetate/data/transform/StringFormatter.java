package org.geoint.acetate.data.transform;

/**
 * Formats data.
 */
@FunctionalInterface
public interface StringFormatter {

    String format(String dataString) throws DataConversionException;
}
