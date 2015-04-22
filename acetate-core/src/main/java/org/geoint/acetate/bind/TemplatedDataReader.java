package org.geoint.acetate.bind;

import org.geoint.acetate.bind.template.DataTemplate;

/**
 * Template-aware data reader.
 *
 * @param <T>
 */
public interface TemplatedDataReader<T> extends StructuredDataReader<T> {

    /**
     * Returns the DataTemplate used by this reader.
     *
     * @return template
     */
    DataTemplate<T> getTemplate();

}
