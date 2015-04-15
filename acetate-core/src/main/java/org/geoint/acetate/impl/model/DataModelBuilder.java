package org.geoint.acetate.impl.model;

import org.geoint.acetate.model.ModelException;

/**
 *
 * @param <T> model type
 * @param <B> child builder type
 */
public interface DataModelBuilder<T, B extends DataModelBuilder> {

    B alias(String name) throws ModelException;

}
