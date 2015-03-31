package org.geoint.acetate.bind;

import java.util.Map;
import org.geoint.util.hierarchy.HierarchicalPath;
import org.geoint.util.hierarchy.impl.AbstractHierarchical;

/**
 *
 */
public class BoundComponent
        extends AbstractHierarchical<BoundComponent> {

    public BoundComponent(HierarchicalPath path,
            Map<String, BoundComponent> children) {
        super(path, children);
    }

}
