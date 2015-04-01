package org.geoint.acetate.impl.bind;

import java.util.Map;
import org.geoint.util.hierarchy.HierarchicalPath;
import org.geoint.util.hierarchy.impl.AbstractHierarchical;

/**
 *
 */
public class BoundComponentImpl
        extends AbstractHierarchical<BoundComponentImpl> {

    public BoundComponentImpl(HierarchicalPath path,
            Map<String, BoundComponentImpl> children) {
        super(path, children);
    }

}
