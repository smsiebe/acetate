package org.geoint.acetate.bind.template;

import java.net.BindException;
import org.geoint.acetate.bind.DataWriter;

/**
 * Template part which only contains static content.
 *
 */
public interface StaticTemplatePart extends TemplatePart {

    /**
     * Write the template static content to the writer.
     *
     * @param writer
     * @throws BindException
     */
    void write(DataWriter writer) throws BindException;
}
