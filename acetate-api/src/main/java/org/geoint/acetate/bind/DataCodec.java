package org.geoint.acetate.bind;

/**
 * Converts data from bytes to a supported data type.
 *
 * @param <T> supported data type
 */
public interface DataCodec<T> {

    T decode(byte[] bytes) throws AcetateCodecException;

    byte[] encode(T data) throws AcetateCodecException;
}
