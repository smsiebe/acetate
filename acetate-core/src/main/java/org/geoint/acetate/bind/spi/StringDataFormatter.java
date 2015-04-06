package org.geoint.acetate.bind.spi;

/**
 * Alternative String formatter for a data type.
 *
 * @param <T> data type
 * @see BinaryDataFormatter
 * @see DataConverter
 * @see ComponentOptions
 * @see BindOptions
 */
public interface StringDataFormatter<T> extends BindingStep {

    /**
     * Supported data type.
     *
     * @return supported data type
     */
    Class<T> getSupportedDataType();

    /**
     * Parses the specialized data format and provides the data in the default
     * data String format.
     *
     * Formatter may return null even if the data provided was not null.
     *
     * @param specializedFormat default data type format
     * @return specialized data format or null
     * @throws FormatException thrown if the data could not be read or the data
     * format could not be completed
     */
    String parse(String specializedFormat) throws FormatException;

    /**
     * Parses the default data type String format and converts it to this
     * specialized data format.
     *
     * Formatter may return null even if the data provided was not null.
     *
     * @param defaultFormat
     * @return specialized data format or null
     * @throws FormatException throws if the data could not be read or the data
     * format could not be completed
     */
    String format(String defaultFormat) throws FormatException;

}
