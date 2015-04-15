package org.geoint.acetate.bind.template;

import java.io.InputStream;
import org.geoint.acetate.model.DataModel;

/**
 * Reads a template and DataModel and interprets a DataTemplate.
 */
public interface TemplateReader {

    /**
     * Media type of the resultant templated data.
     *
     * @return media type of the templated data
     */
    String getDataMediaType();

    /**
     * The file extension used for data from this template.
     *
     * @return file extension for this data type
     */
    String getDataFileExtension();

    /**
     * Reads a template from the stream.
     *
     * @param <T> model type
     * @param in template stream
     * @param type data model type
     * @return template for this model
     * @throws TemplateException thrown if there are any problems reading the
     * data template
     */
    <T> DataTemplate<T> readTemplate(InputStream in, Class<T> type)
            throws TemplateException;

    /**
     * Reads a template from the stream.
     *
     * @param <T> model type
     * @param in template stream
     * @param model data model
     * @return template for this model
     * @throws TemplateException thrown if there are any problems reading the
     * data template
     */
    <T> DataTemplate<T> readTemplate(InputStream in, DataModel<T> model)
            throws TemplateException;

}
