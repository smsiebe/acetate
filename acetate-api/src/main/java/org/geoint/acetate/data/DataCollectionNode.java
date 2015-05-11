package org.geoint.acetate.data;

import java.util.Collection;

/**
 * A wrapper node which allows one or more instances of a node to be mapped to a
 * structural position.
 *
 * @param <T> data type
 */
public interface DataCollectionNode<T> extends DataNode<Collection<T>> {

}
