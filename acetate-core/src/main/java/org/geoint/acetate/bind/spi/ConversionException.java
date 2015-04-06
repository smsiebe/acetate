package org.geoint.acetate.bind.spi;

import org.geoint.acetate.AcetateException;

/**
 * Thrown if the data conversion could not be completed.
 */
public class ConversionException extends AcetateException {

    private final Class<? extends DataConverter> converter;

    public ConversionException(Class<? extends DataConverter> converter) {
        this.converter = converter;
    }

    public ConversionException(Class<? extends DataConverter> converter,
            String message) {
        super(message);
        this.converter = converter;
    }

    public ConversionException(Class<? extends DataConverter> converter,
            String message, Throwable cause) {
        super(message, cause);
        this.converter = converter;
    }

    public ConversionException(Class<? extends DataConverter> converter,
            Throwable cause) {
        super(cause);
        this.converter = converter;
    }

    public Class<? extends DataConverter> getConverter() {
        return converter;
    }

}
