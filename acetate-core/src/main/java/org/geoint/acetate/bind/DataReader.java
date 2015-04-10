package org.geoint.acetate.bind;

import java.nio.ByteBuffer;
import org.geoint.acetate.model.ComponentModel;

/**
 *
 */
public interface DataReader {

    ComponentModel next();

    int length();

    int read(ByteBuffer buffer);

    Object read();
}
