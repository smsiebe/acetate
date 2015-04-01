package org.geoint.acetate.model;

import java.util.Collection;

/**
 *
 */
public interface ClassModel extends ComponentModel {

    /**
     * Return all components of this class.
     *
     * @return all components of the class or an empty collection
     */
    Collection<ComponentModel> getComponents();

    /**
     * Return any component by this name.
     *
     * @param relativeName name of the component
     * @return any model component matching the name or null
     */
    ComponentModel getComponent(String relativeName);

    /**
     * Return all aggregate components of the immediate model (non-recursive).
     *
     * @return all aggregates of the component or an empty collection if there
     * are none
     */
    Collection<ClassModel> getAggregates();

    /**
     * Return the aggregate requested or null if there isn't an aggregate by the
     * requested component name.
     *
     * @param componentName child component name
     * @return aggregate or null if no aggregate by the requested component name
     */
    ClassModel getAggregate(String componentName);

    /**
     * Return all immediate fields of this component (non-recursive).
     *
     * @return all field components of this component or an empty collection
     */
    Collection<FieldModel> getFields();

    /**
     * Return the requested component field or null if the requested component
     * does not exist or isn't a field.
     *
     * @return field or null.
     */
    FieldModel getField();
}
