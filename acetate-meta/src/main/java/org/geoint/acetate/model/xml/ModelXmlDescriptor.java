package org.geoint.acetate.model.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.geoint.acetate.model.ModelType;

/**
 * Reads/writes the model type as an XML file.
 */
public final class ModelXmlDescriptor {

    public static final String MODEL_DESCRIPTOR_RELATIVE_PATH
            = "META-INF/acetate-models";

    /**
     * Writes the model descriptor xml to the output stream.
     *
     * @param typeModel
     * @param out
     * @throws IOException
     */
    public static void write(ModelType typeModel, OutputStream out)
            throws IOException {
        kh
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the ModelType from the xml descriptor.
     *
     * @param in
     * @return type model
     * @throws IOException
     */
    public static ModelType read(InputStream in)
            throws IOException {
        kh
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the relative model descriptor path for the model type.
     *
     * @param typeModel
     * @return
     */
    public static String descriptorPath(ModelType typeModel) {
        return String.join("/", MODEL_DESCRIPTOR_RELATIVE_PATH,
                typeModel.getTypeName());
    }
}
