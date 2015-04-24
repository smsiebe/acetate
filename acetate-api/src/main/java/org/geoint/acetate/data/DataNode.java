package org.geoint.acetate.data;

/**
 * A node of the data graph.
 */
public interface DataNode {

    /**
     * A dot-separated structural position of a data node.
     *
     * Note that multiple data nodes may have the same position (such as the
     * case of an array or map).
     *
     * @return structural position of node
     */
    String getPosition();
}
