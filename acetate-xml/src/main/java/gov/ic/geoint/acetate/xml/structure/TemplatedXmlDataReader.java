package gov.ic.geoint.acetate.xml.structure;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Optional;
import static javafx.scene.input.KeyCode.T;
import org.geoint.acetate.bind.DataBindException;
import org.geoint.acetate.structure.StructureType;
import org.geoint.acetate.bind.TemplatedDataReader;
import org.geoint.acetate.bind.template.DataTemplate;
import org.geoint.acetate.model.DataModel;
import org.geoint.acetate.transform.StringConverter;
import org.geoint.acetate.transform.DataTransformException;

/**
 * A DataReader which makes use of a DataModel and template to read the xml
 * content of a data stream.
 *
 * @param <T> data model type
 */
public class TemplatedXmlDataReader<T> implements TemplatedDataReader<T> {

    private final DataTemplate<T> template;

    public TemplatedXmlDataReader(DataTemplate<T> template)
            throws DataBindException {
        if (template.getModel() == null) {
            throw new DataBindException("Unable to read templated XML data, "
                    + "data model is known.");
        }
        this.template = template;
    }

    public static TemplatedXmlDataReader withTemplate(InputStream template,
            Class<T> modelType) throws DataBindException {

    }

    public static TemplatedXmlDataReader withTemplate(InputStream template,
            DataModel<T> model) throws DataBindException {

    }

    @Override
    public DataTemplate<T> getTemplate() {
        return template;
    }

    @Override
    public StructureType next(boolean includeUnstructured) throws DataBindException {

    }

    @Override
    public boolean isUnstructured() throws DataBindException {

    }

    @Override
    public Optional<DataModel<T>> getModel() {

    }

    @Override
    public Optional<Object> value() throws DataBindException, DataTransformException {

    }

    @Override
    public Optional<String> valueAsString() throws DataBindException, DataTransformException {

    }

    @Override
    public Optional<String> valueAsString(StringConverter formatter) throws DataBindException, DataTransformException {

    }

    @Override
    public StructureType next() throws DataBindException {

    }

    @Override
    public Optional<String> position() throws DataBindException {

    }

    @Override
    public int length() throws DataBindException {

    }

    throw new UnsupportedOperationException(
            

    "Not supported yet."); //To change body of generated methods, choose Tools | Templates.


    @Override
    public int read(ByteBuffer buffer) throws DataBindException {

    }

    @Override
    public int remaining() throws DataBindException {

    }

    @Override
    public ByteBuffer read() throws DataBindException {

    }

}
