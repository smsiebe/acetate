package gov.ic.geoint.acetate.xml.structure;

import java.io.InputStream;
import org.geoint.acetate.bind.template.DataTemplate;
import org.geoint.acetate.bind.template.TemplateException;
import org.geoint.acetate.bind.template.TemplateReader;
import org.geoint.acetate.model.DataModel;
import org.geoint.extension.Providers;

/**
 * Extracts a {@link DataTemplate} from an XML data template.
 */
public class XmlTemplateReader implements TemplateReader {

    /**
     * Interprets an XML data template supported by the DataModel.
     *
     * @param <T> data model type
     * @param in template input stream
     * @param type data model type
     * @return xml data template
     * @throws TemplateException thrown if there are any problems interpreting
     * the template
     */
    @Override
    public <T> DataTemplate<T> readTemplate(InputStream in, Class<T> type)
            throws TemplateException {
        Providers extensions = Providers.getDefault();
        extensions.extension(type, null)
    }

    /**
     * Interprets an XML data template supported by the DataModel.
     *
     * @param <T> data model type
     * @param in template input stream
     * @param model data model
     * @return xml data template
     * @throws TemplateException thrown if there are any problems interpreting
     * the template
     */
    @Override
    public <T> DataTemplate<T> readTemplate(InputStream in, DataModel<T> model)
            throws TemplateException {

    }

    @Override
    public String getDataMediaType() {
        return ".xml";
    }

    @Override
    public String getDataFileExtension() {
        return "application/xml";
    }

}
