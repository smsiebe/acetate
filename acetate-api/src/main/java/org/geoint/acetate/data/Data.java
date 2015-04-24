package org.geoint.acetate.data;

import java.util.Collection;

/**
 * Root of a generic data graph.
 */
public interface Data {

    /**
     * Return the root data node.
     * 
     * @return root data node
     */
    DataNode getDataRoot();
    
    /**
     * Returns all data nodes at the specified position.
     * 
     * @param position dot-separated structural position
     * @return nodes at the specified position
     */
    Collection<DataNode> byPosition(String position);
    
}
