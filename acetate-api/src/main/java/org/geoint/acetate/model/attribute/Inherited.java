package org.geoint.acetate.model.attribute;

import org.geoint.acetate.model.ObjectModel;

/**
 * Data component attribute indicating the component was inherited from a parent
 * component.
 *
 * This attribute is added to a component by the aceteate framework.
 */
public final class Inherited implements ComponentAttribute {

    /**
     * Domain object model for the inherited object.
     *
     * @return inherited (parent) model
     */
    private final ObjectModel<?> inheritedModel;

    public Inherited(ObjectModel<?> inheritedModel) {
        this.inheritedModel = inheritedModel;
    }

    public ObjectModel<?> getInheritedModel() {
        return inheritedModel;
    }

}
