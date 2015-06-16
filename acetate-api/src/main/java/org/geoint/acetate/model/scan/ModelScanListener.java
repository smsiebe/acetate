package org.geoint.acetate.model.scan;

import org.geoint.acetate.DomainRegistry;
import org.geoint.acetate.model.ModelComponent;

/**
 * A callback listener which is notified when model components that are
 * discovered by a {@link ModelScanner scanner}.
 *
 * @see ModelScanner
 * @see DomainRegistry
 */
@FunctionalInterface
public interface ModelScanListener {

    /**
     * Called to indicate that a scan has been started.
     *
     * @param scanner
     */
    default void scanStarted(ModelScanner scanner) {
    }

    /**
     * Called for all listeners registered to a scanner prior to calling {@link #component(String, long,ModelComponent)
     * } on any listener.
     *
     * Component validation allows the listener to reject the component by
     * throwing a ModelComponentRejectedException, explaining the cause for
     * rejection in the exception message.
     *
     * @param component component to validate
     * @throws ModelComponentRejectedException thrown if the individual
     * component must not be included with the domain model
     */
    default void validate(ModelComponent component)
            throws ModelComponentRejectedException {
    }

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
     * Called to indicate that the scanner has completed scanning.
     *
     * @param scanner
     * @param results scan results
     */
    default void scanComplete(ModelScanner scanner, ModelScanResults results) {
    }
}
