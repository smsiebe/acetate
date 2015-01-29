package org.geoint.acetate.spi.template;

import java.io.IOException;
import java.io.InputStream;
import java.util.ServiceLoader;
import org.geoint.acetate.template.DataTemplate;

/**
 * Provider of data template plugins, loaded by the {@link ServiceLoader}.
 */
public interface TemplateProvider {

    /**
     * Determines if this provider can provide a {@link DateTemplate} for the
     * content of the file, based on the provided file name.
     *
     * @param fn template file name
     * @return true if this provider can return a DataTemplate for this template
     * file
     */
    boolean isTemplate(String fn);

    /**
     * Loads the content of the template into a DataTemplate.
     *
     * @param in template file content
     * @return DataTemplate instance
     * @throws IOException thrown if there are problems reading the template
     * content
     * @throws InvalidTemplateException thrown if the provider cannot load the
     * template because the template file was invalid for this provider
     */
    DataTemplate load(InputStream in) throws IOException, InvalidTemplateException;
}
