package org.geoint.acetate.codec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Abstract stream codec.
 *
 * @param <F> the data item type
 */
public abstract class AbstractStreamCodec<F> implements StreamCodec<F> {

    protected static final int DEFAULT_BUFFER_SIZE = 1024;
    protected final int bufferSize;

    public AbstractStreamCodec() {
        this(DEFAULT_BUFFER_SIZE);
    }

    public AbstractStreamCodec(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    @Override
    public byte[] convert(F dataItem) throws AcetateTransformException {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream();
                WritableByteChannel channel = Channels.newChannel(os)) {
            this.to(dataItem, channel);
            return os.toByteArray();
        } catch (IOException ex) {
            //we shouldn't get here, since we're writing to heap
            throw new AcetateTransformException(this.getClass(),
                    dataItem.getClass(), ByteBuffer.class,
                    "Unexpected problem writing an acetate transform to heap"
                    + " memory.", ex);
        }
    }

    @Override
    public F invert(byte[] acetate) throws AcetateTransformException {
        try (ByteArrayInputStream is = new ByteArrayInputStream(acetate);
                ReadableByteChannel channel = Channels.newChannel(is)) {
            return this.from(channel);
        } catch (IOException ex) {
            //we shouldn't get here since we're reading from heap
            throw new AcetateTransformException(this.getClass(),
                    ByteBuffer.class, Object.class, //TODO grab data item type from data metamodel
                    "Unexpected problem reading an acetate layer from heap "
                    + "memory.", ex);
        }
    }

}
