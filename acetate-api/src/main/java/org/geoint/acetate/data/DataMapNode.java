package org.geoint.acetate.data;

import java.util.Map;

/**
 * A node which maps zero or more pairs of data components as a map.
 * 
 * @param <K> data type of the map key
 * @param <V> data type of the map value
 */
public interface DataMapNode<K, V>
        extends DataNode<Map<K, V>>, Map<DataNode<K>, DataNode<V>> {

}
