package org.geoint.acetate.model;

/**
 *
 */
public interface ClassModel extends ModelComponent {

    /**
     * Return all immediate fields of this component (non-recursive).
     *
     * @return all field components of this component or an empty array
     */
    FieldModel[] getFields();

    /**
     * Return the requested component field or null if the requested component
     * does not exist or isn't a field.
     *
     * @return field or null.
     */
    FieldModel getField();
}
