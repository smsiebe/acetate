package org.geoint.acetate.bind.spi;

import org.geoint.acetate.AcetateException;

/**
 * Thrown when the data formatting could not be completed.
 */
public class FormatException extends AcetateException {

    private final Class<?> formatter;

    public FormatException(Class<?> formatter) {
        this.formatter = formatter;
    }

    public FormatException(Class<?> formatter, String message) {
        super(message);
        this.formatter = formatter;
    }

    public FormatException(Class<?> formatter, String message, Throwable cause) {
        super(message, cause);
        this.formatter = formatter;
    }

    public FormatException(Class<?> formatter, Throwable cause) {
        super(cause);
        this.formatter = formatter;
    }

    public Class<?> getFormatter() {
        return formatter;
    }

}
