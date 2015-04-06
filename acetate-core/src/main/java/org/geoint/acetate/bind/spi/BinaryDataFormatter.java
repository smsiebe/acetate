package org.geoint.acetate.bind.spi;

/**
 * Alternative binary representation of a data type.
 *
 * @param <T> data type
 * @see ComponentOptions
 * @see StringDataFormatter
 * @see DataConverter
 */
public interface BinaryDataFormatter<T> extends BindingStep {

    /**
     * Supported data type.
     *
     * @return supported data type
     */
    Class<T> getSupportedDataType();

    /**
     * Parses the specialized data format and provides the data in the default
     * data binary format.
     *
     * Formatter may return null even if the data provided was not null.
     *
     * @param specializedFormat default data type format
     * @return specialized data format or null
     * @throws FormatException thrown if the data could not be read or the data
     * format could not be completed
     */
    byte[] parse(byte[] specializedFormat) throws FormatException;

    /**
     * Parses the default data type binary format and converts it to this
     * specialized data format.
     *
     * Formatter may return null even if the data provided was not null.
     *
     * @param defaultFormat
     * @return specialized data format or null
     * @throws FormatException throws if the data could not be read or the data
     * format could not be completed
     */
    byte[] format(byte[] defaultFormat) throws FormatException;
}
