package org.geoint.acetate.impl.model;

import org.geoint.acetate.codec.ObjectCodec;
import org.geoint.acetate.model.ComponentConstraint;
import org.geoint.acetate.model.attribute.ComponentAttribute;

/**
 *
 *
 * @param <T> component data type
 */
public interface ComponentModelBuilder<T> {

    ComponentModelBuilder constraint(ComponentConstraint constraint);

    ComponentModelBuilder attribute(ComponentAttribute attribute);

    ComponentModelBuilder composite(String localName, String componentName);

    ComponentModelBuilder<T> codec(ObjectCodec<T> codec);
}
