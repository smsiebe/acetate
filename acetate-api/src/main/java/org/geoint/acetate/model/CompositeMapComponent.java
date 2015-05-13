package org.geoint.acetate.model;

/**
 * A composite component which defines a map between two component models.
 *
 * @param <K> data type for the key component
 * @param <V> data type of the value component
 */
public interface CompositeMapComponent<K, V> extends CompositeComponent {

    /**
     * The component model for the map key.
     *
     * @return model of the map key
     */
    ComponentModel<K> getKeyModel();

    /**
     * The context of the key component.
     *
     * The context returned by this method is the "resultant" context which
     * should be used for the component in this context, rather than the default
     * context returned from the ComponentModel of the key.
     *
     * @return map key component context used within context of this composite
     */
    ComponentContext<K> getKeyContext();

    /**
     * They component model for the map value.
     *
     * @return model of the map value
     */
    ComponentModel<V> getValueModel();

    /**
     * The context of the value component.
     *
     * The context returned by this method is the "resultant" context which
     * should be used for the component in this context, rather than the default
     * context returned from the ComponentModel of the value.
     *
     * @return map value component context used within context of this composite
     */
    ComponentContext<K> getValueContext();
}
