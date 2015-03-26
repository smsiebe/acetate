
package org.geoint.acetate.impl.bind;

import java.util.Collection;
import java.util.Map;
import org.geoint.acetate.bind.BoundComponent;
import org.geoint.acetate.bind.BoundData;
import org.geoint.acetate.metamodel.DataModel;
import org.geoint.util.hierarchy.impl.StandardHierarchy;

/**
 * Binds data + DataModel to a Java type.
 * 
 * @param <T>
 */
public class BoundObjectData<T> implements BoundData<T> {

    private final DataModel model;
    private final StandardHierarchy<BoundComponent> data;

    public BoundObjectData(DataModel model, 
            StandardHierarchy<BoundComponent> data) {
        this.model = model;
        this.data = data;
    }

    public BoundObjectData(DataModel model, 
            Map<String, String> data) {
        this.model = model;
        this.data = StandardHierarchy.builder();
    }
    
    
    
    
    @Override
    public DataModel<T> getModel() {

    }

    @Override
    public T asObject() {

    }

    @Override
    public BoundComponent<?> getComponent(String path) {

    }

    @Override
    public Collection<? extends BoundComponent<?>> getComponentCollection(
            String path) {

    }

}
