package org.geoint.acetate.model;

import org.geoint.util.hierarchy.Hierarchical;

/**
 *
 */
public interface ModelComponent extends Hierarchical {

    /**
     * Return all aggregate components of the immediate model (non-recursive).
     *
     * @return all aggregates of the component or an empty array if there are
     * none
     */
    ClassModel[] getAggregates();

    /**
     * Return the aggregate requested or null if there isn't an aggregate by the
     * requested component name.
     *
     * @param componentName child component name
     * @return aggregate or null if no aggregate by the requested component name
     */
    ClassModel getAggregate(String componentName);
}
