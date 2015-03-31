package org.geoint.acetate.bind;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Binds a data to a model creating {@link BoundData}.
 *
 * @param <T> java object data type this binder binds to
 */
public interface DataBinder<T> {

    /**
     * The media type of the resultant data.
     *
     * @return media type
     */
    String getMediaType();

    BoundData bind(InputStream in)
            throws IOException;

    BoundData bind(ReadableByteChannel channel)
            throws IOException;

    BoundData bind(BoundData bound);

    void bind(InputStream in, OutputStream out)
            throws IOException;

    void bind(ReadableByteChannel in, WritableByteChannel out)
            throws IOException;

    void bind(BoundData bound, OutputStream out)
            throws IOException;

    void bind(BoundData bound, WritableByteChannel out)
            throws IOException;
}
