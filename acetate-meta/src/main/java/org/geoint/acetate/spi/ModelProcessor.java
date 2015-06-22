package org.geoint.acetate.spi;

import java.util.ServiceLoader;
import org.geoint.acetate.meta.model.MetaModel;

/**
 * Processes a {@link MetaModel}.
 *
 * Instances of ModelProcessor are loaded through the {@link ServiceLoader}.
 */
public interface ModelProcessor {

    /**
     * Supported meta model name.
     *
     * @return model name this processor supports
     */
    String supportedModel();

    /**
     * Supported version(s).
     *
     * @return model version(s) this processor supports
     */
    String supportedVersion();

    /**
     * Processes the meta model.
     *
     * @param model
     */
    void process(MetaModel model);

}
