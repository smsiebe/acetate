package org.geoint.acetate.impl.model;

import org.geoint.concurrent.NotThreadSafe;

/**
 *
 * @param <T> value type
 */
@NotThreadSafe
public interface ValueModelBuilder<T>
        extends DataModelBuilder<T, ValueModelBuilder<T>> {

}
