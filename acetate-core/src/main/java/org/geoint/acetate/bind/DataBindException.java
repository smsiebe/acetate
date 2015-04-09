package org.geoint.acetate.bind;

import org.geoint.acetate.AcetateException;

/**
 * Thrown if there are problems binding data.
 */
public class DataBindException extends AcetateException {

    private final Class<? extends DataBinder> binder;

    public DataBindException(Class<? extends DataBinder> binder) {
        this.binder = binder;
    }

    public DataBindException(Class<? extends DataBinder> binder,
            String message) {
        super(message);
        this.binder = binder;
    }

    public DataBindException(Class<? extends DataBinder> binder,
            String message, Throwable cause) {
        super(message, cause);
        this.binder = binder;
    }

    public DataBindException(Class<? extends DataBinder> binder,
            Throwable cause) {
        super(cause);
        this.binder = binder;
    }

    public Class<? extends DataBinder> getBinder() {
        return binder;
    }

}
