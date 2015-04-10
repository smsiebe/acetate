package org.geoint.acetate.bind.bound;

import org.geoint.acetate.bind.DataBindException;
import org.geoint.acetate.bind.DataWriter;
import org.geoint.acetate.bound.BoundData;

/**
 *
 */
public interface BoundDataWriter extends DataWriter {

    BoundData getBoundData() throws DataBindException;

}
