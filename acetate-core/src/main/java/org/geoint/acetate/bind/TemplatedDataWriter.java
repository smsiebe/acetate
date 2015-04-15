package org.geoint.acetate.bind;

import org.geoint.acetate.bind.template.DataTemplate;

/**
 * Template-aware data writer.
 *
 * @param <T> data model type
 */
public interface TemplatedDataWriter<T> extends StructuredDataWriter<T> {

    /**
     * Returns the template used by this writer.
     *
     * @return data template
     */
    DataTemplate<T> getTemplate();
}
