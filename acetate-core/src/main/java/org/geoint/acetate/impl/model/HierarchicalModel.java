package org.geoint.acetate.impl.model;

import java.util.Collection;
import org.geoint.acetate.metamodel.ModelComponent;
import org.geoint.util.hierarchy.Hierarchical;
import org.geoint.util.hierarchy.HierarchicalPath;
import org.geoint.util.hierarchy.HierarchicalVisitor;
import org.geoint.util.hierarchy.Hierarchy;

/**
 * Model backed by a {@link Hierarchy}. 
 */
public class HierarchicalModel implements Hierarchical<ModelComponent> {

    @Override
    public HierarchicalPath getPath() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<ModelComponent> getChildren() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(HierarchicalVisitor<ModelComponent> visitor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(HierarchicalPath path, HierarchicalVisitor<ModelComponent> visitor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
