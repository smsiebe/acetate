package org.geoint.acetate.model;

/**
 * A composite component which defines a map between two component models.
 *
 * @param <K> data type for the key component
 * @param <V> data type of the value component
 */
public interface MapCompositeComponent<K, V> extends CompositeComponent {

    /**
     * The component model for the map key.
     *
     * @return model of the map key
     */
    ComponentModel<K> getKeyModel();

    /**
     * They component model for the map value.
     *
     * @return model of the map value
     */
    ComponentModel<V> getValueModel();
}
