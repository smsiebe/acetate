package org.geoint.acetate.impl.bind;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Binds a data to a model creating {@link BoundDataImpl}.
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

    BoundDataImpl bind(InputStream in)
            throws IOException;

    BoundDataImpl bind(ReadableByteChannel channel)
            throws IOException;

    BoundDataImpl bind(BoundDataImpl bound);

    void bind(InputStream in, OutputStream out)
            throws IOException;

    void bind(ReadableByteChannel in, WritableByteChannel out)
            throws IOException;

    void bind(BoundDataImpl bound, OutputStream out)
            throws IOException;

    void bind(BoundDataImpl bound, WritableByteChannel out)
            throws IOException;
}
