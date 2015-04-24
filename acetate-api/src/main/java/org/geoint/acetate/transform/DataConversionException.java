package org.geoint.acetate.transform;

import gov.ic.geoint.acetate.AcetateException;

/**
 * Thrown if there was a problem converting data from one type to another.
 */
public class DataConversionException extends AcetateException {

    private final Class<? extends BinaryConverter> converterType;

    public DataConversionException(
            Class<? extends BinaryConverter> converterType) {
        this.converterType = converterType;
    }

    public DataConversionException(
            Class<? extends BinaryConverter> converterType, String message) {
        super(message);
        this.converterType = converterType;
    }

    public DataConversionException(
            Class<? extends BinaryConverter> converterType, String message,
            Throwable cause) {
        super(message, cause);
        this.converterType = converterType;
    }

    public DataConversionException(
            Class<? extends BinaryConverter> converterType, Throwable cause) {
        super(cause);
        this.converterType = converterType;
    }

    /**
     * The converter that attempted the conversion.
     *
     * @return converter type
     */
    public Class<? extends BinaryConverter> getConverterType() {
        return converterType;
    }
}
