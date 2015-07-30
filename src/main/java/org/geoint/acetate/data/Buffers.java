package org.geoint.acetate.data;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ReadableByteChannel;

/**
 *
 */
public class Buffers {

    public static void readFully(CharBuffer buffer, Readable readable)
            throws IOException {
        final int limit = buffer.limit();
        int total = 0;
        while (total < limit && (readable.read(buffer)) != -1) {
            total += readable.read(buffer);
        }
    }

    /**
     * Reads all
     *
     * @param channel
     * @param numBytes
     * @return
     * @throws IOException
     */
    private ByteBuffer readFully(ReadableByteChannel channel, int numBytes)
            throws IOException {
        ByteBuffer buff = ByteBuffer.allocate(numBytes);
        int read = 0;
        while ((read = channel.read(buff)) != -1 || buff.remaining() > 0);
        if (read == -1 && buff.remaining() > 0) {
            throw new IOException(String.format("Channel does not contain "
                    + "the expected number of bytes (%d$)", numBytes));
        }
        return buff;
    }

}
