package org.geoint.acetate.model.attribute;

/**
 * Data component attribute indicating it was inherited, and where it was
 * inherited from.
 *
 */
public final class Inherited implements ComponentAttribute {

    /**
     * Domain object name from which this component was inherited.
     *
     * @return inherited (parent) component name
     */
    private final String inheritedFrom;

    public Inherited(String inheritedFrom) {
        this.inheritedFrom = inheritedFrom;
    }

    public String getInheritedFrom() {
        return inheritedFrom;
    }

}
