package org.geoint.acetate.impl.bind;

import java.util.LinkedHashMap;
import org.geoint.acetate.model.DataModel;
import org.geoint.util.hierarchy.Hierarchical;
import org.geoint.util.hierarchy.HierarchicalPath;
import org.geoint.util.hierarchy.Hierarchy;
import org.geoint.util.hierarchy.HierarchyBuilder;

/**
 * 
 */
public final class BoundDataImpl
        extends BoundComponentImpl
        implements Hierarchy<BoundComponentImpl> {

    private final DataModel model;

    private BoundDataImpl(DataModel model) {
        super(HierarchicalPath.ROOT, new LinkedHashMap<>());
        this.model = model;
    }

    public static BoundDataBuilder builder(DataModel m) {
        return new BoundDataBuilder(m);
    }

    public static class BoundDataBuilder
            implements HierarchyBuilder<BoundComponentImpl, BoundDataBuilder> {

        private BoundDataImpl data;
        private HierarchicalPath position;

        public BoundDataBuilder(DataModel m) {
            data = new BoundDataImpl(m);
            position = HierarchicalPath.ROOT;
        }

        @Override
        public BoundDataBuilder child(String childName) {
            this.position = position.getChild(childName);
            return this;
        }

        @Override
        public BoundDataBuilder sibling(String siblingName) {
            this.position = position.getParent().getChild(siblingName);
            return this;
        }

        @Override
        public BoundDataBuilder parent() {
            this.position = position.getParent();
            return this;
        }
        
        public BoundDataBuilder setValue (String utf8) {
            
        }
        
        public BoundDataBuilder setValue (byte[] bytes) {
            
        }
        
        public BoundDataBuilder setValue (Object obj) {
            
        }

        @Override
        public BoundDataBuilder merge(Hierarchical<BoundComponentImpl> donor,
                final boolean overwrite) {
            throw new UnsupportedOperationException();
        }

        @Override
        public BoundDataImpl build() {
            try {
                return data;
            } finally {
                data = null;
            }
        }

    }
}
