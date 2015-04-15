package org.geoint.acetate.bind.template;

import java.nio.ByteBuffer;
import java.util.Optional;
import org.geoint.acetate.bind.DataBindException;
import org.geoint.acetate.bind.DataWriter;
import org.geoint.acetate.model.ValueModel;

/**
 * Template part containing a {@link ValueModel value}, optionally wrapped with
 * prefix and suffix static template content.
 */
public interface WrappedTemplateValuePart extends TemplatePart {

    /**
     * Template position of the part.
     *
     * @return position as a dot-separated list of names
     */
    String getPosition();

    /**
     * Model of this template part, if applicable.
     *
     * @return
     */
    Optional<ValueModel> getModel();

    /**
     * Writes the templated value to the writer.
     *
     * @param writer
     * @param data
     * @throws DataBindException
     */
    void write(DataWriter writer, ByteBuffer data) throws DataBindException;

    /**
     * Writes the templated value to the writer.
     *
     * @param writer
     * @param buffer
     * @param offset
     * @param length
     * @throws DataBindException
     */
    void write(DataWriter writer, ByteBuffer buffer, int offset, int length)
            throws DataBindException;

}
