package org.geoint.acetate.model.attribute;

import org.geoint.acetate.model.ContextType;
import org.geoint.acetate.model.ModelComponent;

/**
 * Attribute used to signify a model component is contextually bound, and may
 * differ from the base component model.
 */
public final class Contextual implements ModelAttribute {

    private final ModelComponent contextModel;
    private final ContextType contextType;
    private final String contextName;

    public Contextual(ModelComponent contextModel, ContextType contextType,
            String contextName) {
        this.contextModel = contextModel;
        this.contextType = contextType;
        this.contextName = contextName;
    }

    public ModelComponent getContextModel() {
        return contextModel;
    }

    public ContextType getContextType() {
        return contextType;
    }

    public String getContextName() {
        return contextName;
    }

}
