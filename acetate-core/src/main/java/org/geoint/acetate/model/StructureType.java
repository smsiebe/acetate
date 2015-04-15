package org.geoint.acetate.model;

/**
 * Type of DataModel structural component.
 */
public enum StructureType {

    ARRAY_START,
    ARRAY_END,
    OBJECT_START,
    OBJECT_END,
    VALUE,
    UNSTRUCTURED_ARRAY_START,
    UNSTRUCTURED_ARRAY_END,
    UNSTRUCTURED_OBJECT_START,
    UNSTRUCTURED_OBJECT_END,
    UNSTRUCTURED_VALUE;
}
