package org.geoint.acetate.model.attribute;

import org.geoint.acetate.model.DomainCompositeObject;

/**
 * Data component attribute indicating the component was derived from a
 * composite relationship, and where it was inherited from.
 */
public class Composited implements ComponentAttribute {

    /**
     * Domain model of the originating composite object.
     *
     * @return composite model
     */
    private final DomainCompositeObject<?> compositeModel;

    public Composited(DomainCompositeObject<?> compositeModel) {
        this.compositeModel = compositeModel;
    }

    public DomainCompositeObject<?> getInheritedModel() {
        return compositeModel;
    }

}
