
package org.geoint.acetate.bind.bound;

import org.geoint.acetate.bind.DataBinder;
import org.geoint.acetate.bound.BoundData;

/**
 *
 */
public interface BoundDataBinder extends DataBinder {

    BoundDataReader reader (BoundData bound);
    
    BoundDataWriter writer();
}
