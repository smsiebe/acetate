package org.geoint.acetate.bind;

import org.geoint.acetate.codec.AcetateTransformException;
import org.geoint.acetate.metamodel.FieldModel;

/**
 * An object used to access and set data values on an data object.
 *
 */
public interface BoundField {

    /**
     * The model used for this field.
     *
     * @return mode for this field
     */
    FieldModel<?, ?> getModel();

    /**
     * Return the java Object value of the field.
     *
     * @return the value of the field
     */
    Object getValue();

    /**
     * Value of this field as extracted from, or prepared for, the template.
     *
     * @return the value either extract from, or prepared for, the template
     * @throws AcetateTransformException if there were errors transforming
     */
    String getTemplateValue() throws AcetateTransformException;

}
