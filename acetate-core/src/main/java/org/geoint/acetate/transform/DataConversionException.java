package org.geoint.acetate.transform;

/**
 * Thrown if there was a problem converting data from one type to another.
 */
public class DataConversionException extends Exception {

    private final Class<?> sourceType;
    private final Class<?> expectedType;
    private final Class<? extends DataConverter> converterType;

    public DataConversionException(Class<?> sourceType, Class<?> expectedType,
            Class<? extends DataConverter> converterType) {
        this(sourceType, expectedType, converterType,
                defaultMessage(sourceType, expectedType, converterType));
    }

    public DataConversionException(Class<?> sourceType, Class<?> expectedType,
            Class<? extends DataConverter> converterType, String message) {
        super(message);
        this.sourceType = sourceType;
        this.expectedType = expectedType;
        this.converterType = converterType;
    }

    public DataConversionException(Class<?> sourceType, Class<?> expectedType,
            Class<? extends DataConverter> converterType, String message,
            Throwable cause) {
        super(message, cause);
        this.sourceType = sourceType;
        this.expectedType = expectedType;
        this.converterType = converterType;
    }

    public DataConversionException(Class<?> sourceType, Class<?> expectedType,
            Class<? extends DataConverter> converterType, Throwable cause) {
        this(sourceType, expectedType, converterType,
                defaultMessage(sourceType, expectedType, converterType),
                cause);
    }

    /**
     * The data source type.
     *
     * @return data source type
     */
    public Class<?> getSourceType() {
        return sourceType;
    }

    /**
     * The expected data type after conversion.
     *
     * @return expected type
     */
    public Class<?> getExpectedType() {
        return expectedType;
    }

    /**
     * The converter that attempted the conversion.
     *
     * @return converter type
     */
    public Class<? extends DataConverter> getConverterType() {
        return converterType;
    }

    private static String defaultMessage(Class<?> sourceType,
            Class<?> expectedType,
            Class<? extends DataConverter> converterType) {
        StringBuilder sb = new StringBuilder();
        sb.append("Data conversion from type '")
                .append(sourceType.getClass().getName())
                .append("' to '")
                .append(expectedType.getClass().getName())
                .append("' could not be accomplished by the '")
                .append(converterType.getClass().getName())
                .append("' converter");
        return sb.toString();
    }
}
