package org.geoint.acetate.bind;

import org.geoint.acetate.AcetateException;
import org.geoint.acetate.bind.spi.DataBinder;

/**
 * Thrown if there are problems binding data.
 */
public class DataBindException extends AcetateException {

    private final Class<DataBinder> binder;

    public DataBindException(Class<DataBinder> binder) {
        this.binder = binder;
    }

    public DataBindException(Class<DataBinder> binder, String message) {
        super(message);
        this.binder = binder;
    }

    public DataBindException(Class<DataBinder> binder, String message, Throwable cause) {
        super(message, cause);
        this.binder = binder;
    }

    public DataBindException(Class<DataBinder> binder, Throwable cause) {
        super(cause);
        this.binder = binder;
    }

    public Class<DataBinder> getBinder() {
        return binder;
    }

}
