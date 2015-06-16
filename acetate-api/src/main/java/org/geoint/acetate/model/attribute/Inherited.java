package org.geoint.acetate.model.attribute;

import org.geoint.acetate.model.ComponentAddress;

/**
 * Component attribute indicating the component model was inherited from a
 * parent component.
 *
 * This attribute is added to a component by the aceteate framework.
 */
public final class Inherited implements ComponentAttribute {

    /**
     * Address of the model component which defines this component.
     *
     * @return inherited model address
     */
    private final ComponentAddress definedBy;

    public Inherited(ComponentAddress definedBy) {
        this.definedBy = definedBy;
    }

    public ComponentAddress getDefinedAddress() {
        return definedBy;
    }

}
