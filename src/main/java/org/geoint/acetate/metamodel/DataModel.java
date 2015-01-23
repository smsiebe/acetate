package org.geoint.acetate.metamodel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import org.geoint.acetate.bind.DataBinder;
import org.geoint.acetate.impl.bind.DefaultDataBinder;
import org.geoint.acetate.codec.AcetateTransformException;
import org.geoint.acetate.template.DataTemplate;

/**
 * Metadata model of a data structure.
 */
public final class DataModel<F> {

    private final Map<String, FieldModel<F, ?, ?>> fields; //key is a field alias

    private DataModel(Map<String, FieldModel<F, ?, ?>> fields) {
        this.fields = fields;
    }

    /**
     * Binds a java object to this model.
     *
     * @param instance object to bind against this model
     * @return binding object
     */
    public DataBinder<F> bind(F instance) {
        DefaultDataBinder binder = new DefaultDataBinder();
        binder.bind(this, instance);
        return binder;
    }

    /**
     * Bind and transform the provided data object to the provided template.
     *
     * @param from data item to source data
     * @param template the format to output the data as
     * @param out where to write the tranformed data
     * @throws AcetateTransformException if there is a problem during
     * encoding/decoding data
     * @throws IOException if there are problem writing
     */
    public void encode(F from, DataTemplate template, OutputStream out)
            throws AcetateTransformException, IOException {
        template.encode(this.bind(from), out);
    }

    /**
     * Bind and transform the formatted data into a data object.
     *
     * @param in formatted data string
     * @param template template used to bind data items
     * @return the bound data
     * @throws AcetateTransformException if there is a problem during
     * encoding/decoding data
     * @throws IOException if there are problem reading
     */
    public DataBinder<F> decode(InputStream in, DataTemplate template)
            throws AcetateTransformException, IOException {
        return template.decode(in, this);
    }

    /**
     * Create a blank data model builder.
     *
     * @return blank data model builder
     */
    public static DataModelBuilder<?> builder() {
        return new DefaultModelBuilder();
    }

    /**
     * Create a new data model by starting with extracting fields out of the
     * provided class.
     *
     * @param <F> object type to model from
     * @param instance object from which to create a model
     * @return builder
     */
    public static <F> DataModelBuilder<F> builder(F instance) {
        return new DefaultModelBuilder(instance.getClass());
    }

    private static class DefaultModelBuilder<F> implements DataModelBuilder<F> {

        private Class<F> modelFrom;

        public DefaultModelBuilder() {
        }

        public DefaultModelBuilder(Class<F> modelFrom) {
            this.modelFrom = modelFrom;
        }

    }
}
