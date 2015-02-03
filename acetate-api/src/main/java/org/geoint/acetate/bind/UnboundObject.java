package org.geoint.acetate.bind;

import java.util.Collection;

/**
 * A complex object structure that was not bound to the data model.
 *
 * This object must be immutable.
 */
public interface UnboundObject {

    /**
     * Child fields found in this data structure.
     *
     * @return non-backed collection of child fields or an empty collection.
     */
    Collection<UnboundData> getFields();
}
