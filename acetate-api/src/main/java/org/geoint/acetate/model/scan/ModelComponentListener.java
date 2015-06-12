package org.geoint.acetate.model.scan;

import org.geoint.acetate.model.ModelComponent;
import org.geoint.acetate.model.ModelException;

/**
 * A callback listener which is notified when model components that are
 * discovered by a {@link ModelScanner scanner}.
 *
 */
@FunctionalInterface
public interface ModelComponentListener {

    /**
     * Called for "accepted" components.
     *
     * @param modelName
     * @param modelVersion
     * @param component
     */
    void component(String modelName, long modelVersion,
            ModelComponent component);

    /**
     * Called for all listeners registered to a scanner prior to 
     * {@link #component(String, long,ModelComponent) }
     * to validate the component.
     *
     * The component can be rejected by a listener by throwing a
     * ModelComponentRejectedException, explaining the cause for rejection.
     *
     * @param component component to validate
     * @throws ModelComponentRejectedException thrown if the individual
     * component must not be included with the domain model
     * @throws ModelException thrown if component must cause scanning to stop
     */
    default void validate(ModelComponent component)
            throws ModelComponentRejectedException, ModelException {
    }
}
