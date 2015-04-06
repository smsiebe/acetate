package org.geoint.acetate.bind.spi;

import java.io.IOException;
import org.geoint.acetate.bind.impl.HierarchicalBoundDataImpl;

/**
 * Binds a data to a model creating {@link HierarchicalBoundDataImpl}.
 *
 */
public interface DataBinder {

    /**
     * The media type of the resultant data.I
     *
     * @return media type
     */
    String getMediaType();

    /**
     * Returns the BindingReader for this binder.
     *
     * @return reader binder reader
     * @throws IOException thrown if the binder is unable to read
     */
    BindingReader reader() throws IOException;

    /**
     * Returns the BindingWriter for this binder.
     *
     * @return binder writer
     * @throws IOException throws if the binder is unable to write
     */
    BindingWriter writer() throws IOException;

}
