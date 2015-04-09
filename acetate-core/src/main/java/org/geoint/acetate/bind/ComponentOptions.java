package org.geoint.acetate.bind;

import org.geoint.acetate.transform.DataConverter;
import org.geoint.acetate.transform.DataFormatter;
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
 * @see BindOptions
 * @see DataConverter
 */
public interface ComponentOptions {

    public static interface DataTransformFlow {
        
    }
    public static interface ComponentOptionsBuilder {

        /**
         * Adds a String formatter to the read chain which will be used to
         * reformat String formatted data value prior to it being set as the
         * value of the field through {@link DataType#asString}.
         *
         * Multiple read formatters may be added as a chain by repeatedly
         * calling #readString.
         *
         * @param formatter data formatter that will be used to read the data
         * @return this (fluid interface)
         */
        ComponentOptions readString(DataFormatter<String> formatter);

        /**
         * Adds a byte formatter to the read chain which will be used to
         * reformat binary formatted data value prior to it being set as the
         * value of the field through {@link DataType#asString}.
         *
         * Multiple read formatters may be added as a chain by repeatedly
         * calling #readBytes.
         *
         * @param formatter formatter that will be used to read the binary data
         * @return this (fluid interface)
         */
        ComponentOptions readBytes(DataFormatter<byte[]> formatter);

        /**
         * Adds a {@link DataConverter} to the field read chain.
         *
         * @param <F> "from" data type
         * @param <T> "to" data type
         * @param converter converter used to convert the data type
         * @return this (fluid interface)
         */
        <F, T> ComponentOptions readConverter(DataConverter<F, T> converter);

        /**
         * Adds a String formatter to the write chain which will be used to
         * reformat String formatted data value prior to it being written to a
         * {@link BindingWriter}.
         *
         * Multiple write formatters may be added as a chain by repeatedly
         * calling #writeString.
         *
         * @param formatter data formatter that will be used to read the data
         * @return this (fluid interface)
         */
        ComponentOptions writeString(DataFormatter<String> formatter);

        /**
         * Adds a byte formatter to the write chain which will be used to
         * reformat binary formatted data value prior to it being written to a
         * {@link BindingWriter}.
         *
         * Multiple write formatters may be added as a chain by repeatedly
         * calling #writeBytes.
         *
         * @param formatter formatter that will be used to read the binary data
         * @return this (fluid interface)
         */
        ComponentOptions writeBytes(DataFormatter<byte[]> formatter);

        /**
         * Adds a {@link DataConverter} to the field write chain.
         *
         * @param <F> "from" data type
         * @param <T> "to" data type
         * @param converter converter used to convert the data type
         * @return this (fluid interface)
         */
        <F, T> ComponentOptions writeConverter(DataConverter<F, T> converter);

        /**
         * Create an instance of ComponentOptions.
         *
         * @return options
         */
        public ComponentOptions build();
    }
}
