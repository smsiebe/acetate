package org.geoint.acetate.model;

import java.lang.reflect.Field;

/**
 *
 */
public interface ModelField<T> extends ModelTypeUse<T>, ModelMember {

    /**
     * Field name used by the declaring type.
     *
     * @return field name
     */
    String getFieldName();

    /**
     *
     * @return Field definition
     */
    Field getField();
}
