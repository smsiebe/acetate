package org.geoint.acetate.bind;

import org.geoint.acetate.bind.spi.BinaryDataFormatter;
import org.geoint.acetate.bind.spi.StringDataFormatter;
import org.geoint.acetate.bind.spi.DataConverter;
import org.geoint.acetate.model.DataType;

/**
 * Providing binding content for a specific component of the data model.
 *
 * <b>IMPORTANT:</b> The order in which the ComponentOptions methods are called
 * corresponds to the order of operations done on to component data read from a
 * DataBinder.
 *
 * <h2>Data Formats</h2>
 *
 * Each data type has a default binary and string representation (format), as
 * well as a corresponding Java Object representation. For simple binding
 * operations this default data formatting might be all that is needed. However,
 * the acetate framework was designed specifically to support non-trivial
 * binding considerations, such as legacy (aka you can't change it) data
 * formats, multiple formats for a single data type (ie protocol/endpoint
 * versions), etc. Acetate supports these with the pluggable
 * {@link BinaryDataFormatter} and {@link StringDataFormatter} interfaces.
 * <p>
 * When a data type need to/is represented as in an alternative format, a
 * formatter may be specified to parse or format the data to/from the default
 * data format and the specialized format.
 *
 *
 * @see BinaryOptions
 * @see StringDataFormatter
 * @see BinaryDataFormatter
 * @see DataConverter
 */
public interface ComponentOptions {

    /**
     * Adds a formatter to the read chain which will be used before
     * {@link DataType#asString}.
     *
     * @param <T> data type of the component
     * @param formatter data formatter that will be used to read the data
     * @return this (fluid interface)
     */
    <T> ComponentOptions read(StringDataFormatter<T> formatter);

    /**
     * Adds a formatter to the read chain which will be used before  
     * {@link DataType#asObject(ByteBuffer) }.
     *
     * @param <T> data type of the component
     * @param formatter formatter that will be used to read the binary data
     * @return this (fluid interface)
     */
    <T> ComponentOptions read(BinaryDataFormatter<T> formatter);

    /**
     * Adds a {@link DataConverter} to the read chain.
     *
     * @param <F> "from" data type
     * @param <T> "to" data type
     * @param converter converter used to convert the data type
     * @return this (fluid interface)
     */
    <F, T> ComponentOptions read(DataConverter<F, T> converter);

    /**
     * Adds a formatter to the write chain which will be used after
     * {@link DataType#asString}.
     *
     * @param <T> data type of the component
     * @param formatter data formatter that will be used to read the data
     * @return this (fluid interface)
     */
    <T> ComponentOptions write(StringDataFormatter<T> formatter);

    /**
     * Adds a formatter to the write chain which will be used after  
     * {@link DataType#asObject(ByteBuffer) }.
     *
     * @param <T> data type of the component
     * @param formatter formatter that will be used to read the binary data
     * @return this (fluid interface)
     */
    <T> ComponentOptions write(BinaryDataFormatter<T> formatter);

    /**
     * Adds a {@link DataConverter} to the write chain.
     *
     * @param <F> "from" data type
     * @param <T> "to" data type
     * @param converter converter used to convert the data type
     * @return this (fluid interface)
     */
    <F, T> ComponentOptions write(DataConverter<F, T> converter);
}
