package org.geoint.acetate.data.transform;

import org.geoint.acetate.io.ByteReader;
import org.geoint.acetate.io.ByteWriter;
import org.geoint.acetate.model.ModelContextPath;
import org.geoint.acetate.model.DomainModel;

/**
 * Converts bytes to objects and objects to bytes.
 *
 * All codecs must be thread-safe.
 *
 * @param <T> data type
 */
public abstract class ObjectCodec<T> {

    protected final DomainModel model;
    protected final ModelContextPath path;

    public ObjectCodec(DomainModel model, ModelContextPath path) {
        this.model = model;
        this.path = path;
    }

    /**
     * Convert binary data in expected format to an object instance.
     *
     * @param reader byte source
     * @return data instance
     * @throws DataConversionException thrown if the source could not be read or
     * the object could not be instantiated
     */
    abstract public T convert(ByteReader reader) throws DataConversionException;

    /**
     * Converts an object to expected binary format.
     *
     * @param data source object
     * @param writer where the bytes are written
     * @throws DataConversionException throws if there are problems writing the
     * object as bytes
     */
    abstract public void invert(T data, ByteWriter writer) throws DataConversionException;
}
