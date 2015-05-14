package org.geoint.acetate.model;

/**
 * Domain model structural component which identifies a key/value map of object
 * types.
 */
public interface ObjectMapModel<K, V> extends ContextualComponent {

    ContextualObjectModel<K> getKeyModel();

    ContextualObjectModel<V> getValueModel();
}
