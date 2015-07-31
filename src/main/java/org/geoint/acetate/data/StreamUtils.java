package org.geoint.acetate.data;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ReadableByteChannel;

/**
 *
 */
public class StreamUtils {

    public static void readFully(CharBuffer buffer, Readable readable)
            throws IOException {
        final int limit = buffer.limit();
        int total = 0;
        while (total < limit && (readable.read(buffer)) != -1) {
            total += readable.read(buffer);
        }
    }
    
    /**
     * Creates a String from all the content available from the Readable 
     * @param readable
     * @return 
     */
    public static String readString (Readable readable) {
        
    }

    /**
     * Returns a new ByteBuffer containing the specified number of bytes in the
     * buffer.
     * <p>
     * The returned ByteBuffer has been flipped and is ready to read the content
     * of the buffer.
     *
     * @param channel
     * @param numBytes
     * @return new byte buffer containing the number of bytes requested
     * @throws IOException thrown if there are problems reading from the channel
     * or the channel does not contain enough bytes
     */
    public static ByteBuffer readFully(ReadableByteChannel channel, int numBytes)
            throws IOException {
        ByteBuffer buff = ByteBuffer.allocate(numBytes);
        readFully(channel, buff);
        return buff;
    }

    /**
     * Reads bytes from the channel into the provided buffer until
     * {@code buffer.remaining() == 0)}.
     *
     * @param channel
     * @param buff
     * @throws IOException thrown if there are problems reading from the channel
     * or the channel does not contain enough bytes
     */
    public static void readFully(ReadableByteChannel channel, ByteBuffer buff)
            throws IOException {
        int read = 0;
        buff.mark();
        while ((read = channel.read(buff)) != -1 || buff.remaining() > 0);
        if (read == -1 && buff.remaining() > 0) {
            buff.reset();
            throw new IOException(String.format("Channel does not contain "
                    + "the expected number of bytes (%d$)", buff.remaining()));
        }
        buff.flip();
    }

}
