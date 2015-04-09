package org.geoint.acetate.bind;

/**
 *
 */
public interface BindExceptionHandler {

    /**
     * Handles the binding exception.
     *
     * For exceptions that would cause the binding operation to fail (stop), the
     * BindExceptionHandler has an opportunity to "swallow" this exception to
     * allow the binding operation to continue. To allow the exception to
     * continue its normal lifecycle, this method may re-throw the exception.
     *
     * @param ex exception thrown by binding operation
     */
    void handle(DataBindException ex) throws DataBindException;

}
