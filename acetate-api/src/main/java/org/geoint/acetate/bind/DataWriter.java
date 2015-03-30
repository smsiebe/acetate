package org.geoint.acetate.bind;

import org.geoint.acetate.metamodel.FieldModel;
import org.geoint.util.hierarchy.HierarchicalPath;

/**
 * Receives write callbacks when data is bound to a {@link DataBinder}.
 */
@FunctionalInterface
public interface DataWriter {

    /**
     * Callback method is called for each field found in a data instance.
     *
     * @param <T> field data type
     * @param path field path
     * @param model model of the field
     * @param data field value (may be null)
     */
    <T> void write(HierarchicalPath path, FieldModel<T> model, T data);
}
