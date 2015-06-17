package org.geoint.acetate.model.attribute;

import org.geoint.acetate.model.ModelComponent;

/**
 * Component attribute indicating the component model was inherited from a
 * parent component.
 *
 * This attribute is added to a component by the aceteate framework.
 */
public final class Inherited implements ModelAttribute {

    /**
     * Model which defines this component.
     *
     * @return inherited model address
     */
    private final ModelComponent definedBy;

    public Inherited(ModelComponent definedBy) {
        this.definedBy = definedBy;
    }

    public ModelComponent getDefinedAddress() {
        return definedBy;
    }

}
