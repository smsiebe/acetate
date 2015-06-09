package org.geoint.acetate.model.attribute;

import org.geoint.acetate.model.DomainObject;

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
    private final DomainObject<?> inheritedModel;

    public Inherited(DomainObject<?> inheritedModel) {
        this.inheritedModel = inheritedModel;
    }

    public DomainObject<?> getInheritedModel() {
        return inheritedModel;
    }

}
