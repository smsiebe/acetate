package org.geoint.acetate.transform;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

/**
 * Converts binary data to an alternative binary <b>WITHOUT</b> advancing the
 * position of the source buffer.
 */
public abstract class BinaryConverter {

    protected ByteBuffer buffer;

    public BinaryConverter(ByteBuffer buffer, int offset, int length) {
        final ByteBuffer bb = buffer.slice();
        bb.position(offset);

        if (bb.remaining() < length) {
            throw new BufferOverflowException();
        }

        bb.limit(length);
        this.buffer = bb;
    }
}
