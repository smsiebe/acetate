package org.geoint.acetate.data;

import java.io.IOException;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.geoint.acetate.domain.annotation.ContentType;
import org.geoint.acetate.domain.annotation.Service;
import org.geoint.acetate.domain.model.DataModel;

/**
 * Converts a data type to/from a bytes/objects.
 *
 * DataCodecs are a service to the domain model, thus annotated as a
 * {@link Service}.
 *
 * @param <T> data type supported
 * @see ContentType
 */
@Service(name = "codec", displayName = "Codec Service",
        domain = DataModel.ACETATE_DOMAIN_NAME,
        version = DataModel.ACETATE_DOMAIN_VERSION)
public interface DataCodec<T> {

    /**
     * Writes the object value to the channel.
     *
     * @param context
     * @param obj
     * @param channel
     * @throws java.io.IOException
     */
    void convert(CodecContext context, T obj,
            WritableByteChannel channel) throws IOException;

    /**
     * Reads the object value from the channel, returning an object instance.
     *
     * @param context
     * @param channel
     * @return object instance
     * @throws java.io.IOException
     */
    T invert(CodecContext context, ReadableByteChannel channel)
            throws IOException;

    /**
     * Provides the estimated data length in bytes for the formatted form of
     * this data type.
     *
     * @param obj
     * @return estimated length or -1 if the estimated length is not known
     */
    default long getEstimatedLength(T obj) {
        return -1;
    }
}
