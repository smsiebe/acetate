package org.geoint.acetate.bind.spi;

/**
 * Converts a data component from one Java Object type to another.
 * <p>
 * Sometimes specialized formatters are not enough and an binding needs just a
 * bit more -- this is where the {@link DataConverter} plugin comes in. When one
 * data model binding produces a model with one data type but another binding is
 * expecting a different data type for that model component, a
 * {@link DataConverter} may be defined in the {@link ComponentOptions} to
 * convert or invert the bound data from one data type to another.
 * <p>
 * Unlike formatters, converters require the bound data to be first converted to
 * a Java Object first, rather than work directly on the binary or character
 * format of a data type. This, coupled with the defined format/conversion
 * procedure, allows applications to defined formatter-converter chains to
 * easily convert data from different formats and types for maximum code reuse
 * potential.
 *
 * @param <F> data type to convert from
 * @param <T> data type to convert to
 * @see StringDataFormatter
 * @see BinaryDataFormatter
 * @see ComponentOptions
 * @see BindingOptions
 */
@FunctionalInterface
public interface DataConverter<F, T> extends BindingStep {

    /**
     * Convert one data type to another.
     *
     * The converter may also return null, even if the data provided to the
     * converter was not null.
     *
     * @param obj data type to convert from
     * @return data type converted to, or null
     * @throws ConversionException thrown if the data conversion could not be
     * complete
     */
    T convert(F obj) throws ConversionException;

}
