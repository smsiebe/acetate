package org.geoint.acetate.codec;

import java.io.IOException;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * A codec capable of stream transformations.
 *
 * @param <F> the java object "from" which to create the alternative data
 * representation
 */
public interface StreamCodec<F> extends AcetateCodec<F, byte[]> {

    /**
     * Converts the data item to the alternative data format as a stream,
     * writing the data to the output channel.
     *
     * @param dataItem data item to convert
     * @param channel channel to write the alternative data format
     * @throws AcetateTransformException thrown if there is a conversion problem
     * @throws IOException thrown if the acetate cannot be written
     */
    void to(F dataItem, WritableByteChannel channel)
            throws AcetateTransformException, IOException;

    /**
     * Reads the alternative data representation from a stream, creating the
     * data item.
     *
     * @param channel channel to read the acetate representation
     * @return the data item
     * @throws AcetateTransformException thrown if there is a conversion problem
     * @throws IOException thrown if there are problems reading from the channel
     */
    F from(ReadableByteChannel channel)
            throws AcetateTransformException, IOException;
}
