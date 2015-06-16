package org.geoint.acetate.model.attribute;

import org.geoint.acetate.model.ComponentAddress;

/**
 * Data component attribute indicating the component was composed from a
 * (composite) object relationship.
 *
 * This attribute is added to a component by the aceteate framework.
 *
 */
public final class Composited implements ComponentAttribute {

    /**
     * Address of the model component which defines this component.
     *
     * @return composite model address
     */
    private final ComponentAddress definedBy;

    public Composited(ComponentAddress definedBy) {
        this.definedBy = definedBy;
    }

    public ComponentAddress getDefinedAddress() {
        return definedBy;
    }
}
