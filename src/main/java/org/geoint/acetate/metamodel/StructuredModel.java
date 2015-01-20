package org.geoint.acetate.metamodel;

import java.util.Collection;

/**
 * A structured data definition.
 */
public interface StructuredModel {

    /**
     * The fields that comprise this model.
     *
     * @param alias field name
     * @return model for a field
     */
    FieldModel<?, ?, ?> getField(String alias);

    /**
     * All models of child fields.
     *
     * @return all child field items
     */
    Collection<FieldModel<?, ?, ?>> getFields();
}
