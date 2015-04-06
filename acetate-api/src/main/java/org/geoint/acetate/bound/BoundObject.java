package org.geoint.acetate.bound;

import java.util.Collection;
import org.geoint.acetate.model.ClassModel;

/**
 *
 */
public interface BoundObject extends BoundComponent<ClassModel> {

    /**
     * Return the child components of this object.
     *
     * @return direct child components
     */
    Collection<BoundComponent> getComponents();

}
