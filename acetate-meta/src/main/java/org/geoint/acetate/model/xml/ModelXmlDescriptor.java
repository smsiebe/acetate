package org.geoint.acetate.model.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import org.geoint.acetate.model.ModelElement;

/**
 * Reads/writes a models meta description to an XML file.
 */
public final class ModelXmlDescriptor {

    public static void write(Collection<ModelElement> models, OutputStream out)
            throws IOException {
        throw new UnsupportedOperationException();
    }

    public static Collection<ModelElement> read(InputStream in)
            throws IOException {
        throw new UnsupportedOperationException();
    }
}
