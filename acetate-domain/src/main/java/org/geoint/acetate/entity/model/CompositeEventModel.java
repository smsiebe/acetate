package org.geoint.acetate.entity.model;

import org.geoint.acetate.model.CompositeComponentModel;

/**
 *
 */
public interface CompositeEventModel<T> extends EventModel<T>,
        CompositeComponentModel<T> {

    @Override
    EventModel<T> getBaseModel();
}
