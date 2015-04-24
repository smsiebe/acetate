package org.geoint.acetate.data;

import java.util.Collection;
import java.util.Set;

/**
 * A node which may contain zero or more child nodes.
 *
 * In practice a ComplexDataNode instance may also be a {@link DataValueNode},
 * allowing the application to either return the data as a java object or
 * continue to handle the data generically.
 */
public interface ComplexDataNode extends DataNode {

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
