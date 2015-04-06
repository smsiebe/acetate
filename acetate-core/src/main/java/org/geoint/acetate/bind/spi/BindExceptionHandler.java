package org.geoint.acetate.bind.spi;

/**
 * Handler/interceptor for warning or fatal binding exceptions that occur during
 * a binding operation.
 */
@FunctionalInterface
public interface BindExceptionHandler {

    /**
     * Handles the binding exception.
     *
     * For exceptions that would cause the binding operation to fail (stop), the
     * BindExceptionHandler has an opportunity to "swallow" this exception to
     * allow the binding operation to continue. To allow the exception to
     * continue its normal lifecycle, this method may re-throw the exception.
     *
     * @param <T> exception type
     * @param ex exception thrown by binding operation
     * @throws T re-thrown exception type
     */
    <T extends Throwable> void handle(T ex) throws T;

}
