package org.geoint.acetate.model.reflect;

import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.stream.Collectors;
import org.geoint.acetate.impl.meta.model.ImmutableObjectModel;
import org.geoint.acetate.impl.meta.model.ModelBuilder;
import org.geoint.acetate.impl.meta.model.ObjectModelBuilder;
import org.geoint.acetate.meta.annotation.Meta;
import org.geoint.acetate.meta.model.MetaModel;
import org.geoint.acetate.meta.model.ObjectModel;

/**
 * Reflects on the provided classes to create metamodel(s) based on the
 * annotated classes.
 *
 */
public class ReflectionModeler {

    /**
     * Model the classes as zero or more meta models.
     *
     * @param classes
     * @return metamodels discovered or an empty collection
     */
    public static Collection<ObjectModel> model(Class<?>... classes) {

        
        ModelBuilder builder = new ModelBuilder();
        
        for (Class<?> toModel : classes) {
            ObjectModelBuilder<?> ob = builder.forClass(toModel);
            
        }

        return (modelCache.get(toModel) != null)
                ? (ImmutableObjectModel<T>) modelCache.get(toModel).get()
                : null;
    }

    private static <T> ObjectModelBuilder<T> reflect(Class<T> toModel) {
        //check if this class has annotated with a Meta annotation, 
        //otherwise return null because this object is not part of a model
        Collection<Annotation> objMetaAnnotations = getMeta(toModel);
        if (objMetaAnnotations.isEmpty()) {
            return null;
        }

    }

    private static Collection<Annotation> getMeta(Class<?> toModel) {
        return Arrays.stream(toModel.getAnnotations())
                .filter((a) -> a.getClass().isAnnotationPresent(Meta.class))
                .collect(Collectors.toList());
    }
}
