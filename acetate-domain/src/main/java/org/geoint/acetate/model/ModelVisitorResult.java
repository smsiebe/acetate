package org.geoint.acetate.model;

/**
 * Returned from model visitor callback methods to shape visitation behavior.
 */
public enum ModelVisitorResult {

    /**
     * Continue visiting the next model components.
     *
     * If returned from a callback method indicating the start of a new composed
     * model (ie object, event, entity) the visitor will continue by visiting
     * the composites of that model.
     */
    CONTINUE,
    /**
     * If returned from a callback method indicating the start of a composed
     * model (ie object, event, entity) the visitor will skip visiting the
     * composite components of that model.
     */
    SKIP,
    /**
     * Shutdown the model visitor.
     */
    STOP;

}
