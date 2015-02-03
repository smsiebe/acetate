package org.geoint.acetate.bind;

/**
 * Data extracted from a template that was not bound to a DataModel.
 *
 * This object must be immutable.
 *
 */
public interface UnboundData {

    /**
     * The name of the field that the data was extracted from the template.
     *
     * @return name of the field as determined by the template
     */
    String getName();

}
