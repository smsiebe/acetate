package org.geoint.acetate.data;

import java.util.Map;
import org.geoint.acetate.model.DataComponent;

/**
 * A node which maps zero or more pairs of data components as a map.
 */
public interface DataMapNode
        extends DataNode, Map<DataComponent<?>, DataComponent<?>> {

}
