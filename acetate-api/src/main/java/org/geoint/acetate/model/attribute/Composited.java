package org.geoint.acetate.model.attribute;

import org.geoint.acetate.model.DomainObject;

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
    private final DomainObject<?> inheritedModel;

    public Composited(DomainObject<?> inheritedModel) {
        this.inheritedModel = inheritedModel;
    }

    public DomainObject<?> getInheritedModel() {
        return inheritedModel;
    }
}
