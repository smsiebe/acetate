package org.geoint.acetate.impl.structure.reflect;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import org.geoint.acetate.impl.model.cache.TypedModelCache;
import org.geoint.acetate.impl.model.cache.AsyncWeakTypedModelCache;
import org.geoint.acetate.impl.model.reflect.DataComponentReflector;
import org.geoint.acetate.impl.structure.StructureComponent;
import org.geoint.acetate.impl.structure.StructureComponentModifier;
import org.geoint.acetate.model.DataComponent;
import org.geoint.acetate.structure.DataStructure;

/**
 * Utilizes reflection to derive both the DataModel components as well as the
 * specific DataStructure of the object graph.
 *
 * @param <T>
 */
public class DataStructureReflector<T> implements Callable<DataStructure> {

    //config
    private final Class<T> rootComponent;
    private final TypedModelCache modelCache;
    private final Map<String, Collection<StructureComponentModifier<?>>> modifiers;

    /**
     * Constructs a reflector that does not use a shared model cache.
     *
     * @param rootComponent
     */
    public DataStructureReflector(Class<T> rootComponent) {
        this(rootComponent, Collections.EMPTY_MAP, new AsyncWeakTypedModelCache());
    }

    public DataStructureReflector(Class<T> rootComponent,
            TypedModelCache modelCache) {
        this(rootComponent, Collections.EMPTY_MAP, modelCache);
    }

    /**
     *
     * @param rootComponent root component
     * @param modifiers data structure modifiers, key is component path
     * @param modelCache
     */
    public DataStructureReflector(Class<T> rootComponent,
            Map<String, Collection<StructureComponentModifier<?>>> modifiers,
            TypedModelCache modelCache) {
        this.rootComponent = rootComponent;
        this.modifiers = modifiers;
        this.modelCache = modelCache;
    }

    @Override
    public DataStructure call() throws Exception {
        DataComponent<T> rComp = getModel(rootComponent);
        StructureComponent<T> rStr = deriveStructure(rComp);
    }

    /**
     * Return the recursive model data component for the specified class.
     *
     * @param rootComponent
     * @return non-structure (model) component definition
     */
    private DataComponent<T> getModel(Class<T> clazz) {
        final DataComponent<T> componentModel =  modelCache.getOrCache(clazz,
                () -> new DataComponentReflector(clazz));
        
        
    }

    /**
     * Derive the context-specific structure for the requested data component
     * given its position and and modifiers set for that position.
     *
     * @param rComp
     * @return
     */
    private StructureComponent<T> deriveStructure(final String position,
            final DataComponent<T> rComp) {
        return 
    }

}
