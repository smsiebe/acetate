package org.geoint.acetate.data;

import java.util.Collection;
import java.util.Set;

/**
 * A node which may contain zero or more child nodes.
 *
 */
public interface CompositeDataNode extends DataNode {

    /**
     * A collection of child data nodes.
     *
     * Changes to the returned collection, or contained nodes, does change the
     * data of this or child data nodes.
     *
     * @return children
     */
    Collection<DataNode> getChildren();

    /**
     * An immutable set of child node names.
     *
     * Changes to the returned set will not change this node.
     *
     * @return child node names
     */
    Set<String> getChildNames();
}
