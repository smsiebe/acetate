package org.geoint.acetate.bind;



import java.io.IOException;

/**
 * Binds a data to a model.
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
