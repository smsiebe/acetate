package org.geoint.acetate.model.attribute;

import org.geoint.acetate.model.ObjectModel;

/**
 * Data component attribute indicating the component was composed from a
 * non-entry aggregate (composite) object.
 *
 * This attribute is added to a component by the aceteate framework.
 *
 */
public final class Composited implements ComponentAttribute {

    /**
     * Domain object model for the inherited object.
     *
     * @return inherited (parent) model
     */
    private final ObjectModel<?> compositeModel;

    public Composited(ObjectModel<?> inheritedModel) {
        this.compositeModel = inheritedModel;
    }

    /**
     * Return the object model that declared the component.
     *
     * @return the object model of the composite that declared the component
     * this attribute decorates
     */
    public ObjectModel<?> getCompositeModel() {
        return compositeModel;
    }
}
