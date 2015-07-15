package org.geoint.acetate.bind.transform;

import java.nio.Buffer;
import org.geoint.acetate.domain.model.DataModel;

/**
 * Codec used to convert a domain model object instance to/from a data stream.
 *
 * All codec implementations must be thread-safe and must be able to be
 * initialized by its no-arg constructor.
 *
 * @param <T> java data type to convert
 * @param <B> buffer type
 */
public interface BufferedCodec<T, B extends Buffer> {

    /**
     * Convert data in expected format to an object instance.
     *
     * @param model component model
     * @param reader data source
     * @return object instance
     * @throws DataTransformException thrown if the source could not be read or
     * the object could not be instantiated
     */
    T convert(DataModel<T> model, B reader)
            throws DataTransformException;

    /**
     * Converts an object to a data, writing the data to the provided writer.
     *
     * @param model component model
     * @param data source object
     * @param writer data writer
     * @throws DataTransformException throws if there are problems writing the
     * object
     */
    void invert(DataModel<T> model, T data, B writer)
            throws DataTransformException;

}
