package org.geoint.acetate.model.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.geoint.acetate.impl.meta.model.ModelBuilder;
import org.geoint.acetate.impl.meta.model.ObjectModelBuilder;
import org.geoint.acetate.meta.annotation.DoNotModel;
import org.geoint.acetate.meta.annotation.Meta;
import org.geoint.acetate.meta.annotation.MetaAttribute;
import org.geoint.acetate.meta.model.ModelException;
import org.geoint.acetate.meta.model.ObjectModel;

/**
 * Reflects on the provided classes to create metamodel(s) based on the
 * annotated classes.
 *
 */
public class ReflectionModeler {

    private static final Logger logger
            = Logger.getLogger(ReflectionModeler.class.getName());

    /**
     * Created object models using reflection.
     *
     * @param classes
     * @return metamodels discovered or an empty collection
     * @throws ModelException thrown if there is an error in model
     */
    public static Collection<ObjectModel> model(Class<?>... classes)
            throws ModelException {

        ModelBuilder builder = new ModelBuilder();

        for (final Class<?> toModel : classes) {

            if (toModel.isAnnotationPresent(DoNotModel.class)) {
                //explicitly declared to not model this class
                logger.fine(() -> "Modeling of class "
                        + toModel.getClass().getName()
                        + " skipped, annotated with DoNotModel");
                continue;
            }
            //check if this class has annotated with a Meta annotation, 
            //otherwise return null because this object is not part of a model
            Collection<Annotation> objMetaAnnotations = getMeta(toModel);
            if (objMetaAnnotations.isEmpty()) {
                logger.fine(() -> toModel.getClass().getName()
                        + " is not defined as a model class; missing Meta metamodel "
                        + "annotation.  Class will not be modeled.");
                continue;
            }

            logger.fine(() -> "Creating model of "
                    + toModel.getClass().getName()
                    + " with reflection.");
            ObjectModelBuilder<?> ob = builder.forClass(toModel);

            //add MetaAttributes annotated on any Meta annotation to 
            //the object model
            for (Annotation a : objMetaAnnotations) {
                for (Method m : a.getClass().getDeclaredMethods()) {
                    try {
                        if (!m.isAnnotationPresent(MetaAttribute.class)) {
                            //not a meta attribute, skip annotation method
                            continue;
                        }
                        MetaAttribute ma = m.getAnnotation(MetaAttribute.class);
                        ob.withAttribute(
                                (!ma.name().isEmpty()
                                        ? ma.name()
                                        : a.getClass().getAnnotation(Meta.class).name()
                                        + "." + m.getName()),
                                String.valueOf(m.invoke(a)));
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        throw new ReflectionModelException(
                                "Unable to resolve meta attribute value while "
                                + "modeling " + toModel.getName(), ex);
                    }
                }
            }

            //add operation models only declared by this class, not for 
            //parent models (this will be added by the builder)
            Arrays.stream(toModel.getDeclaredMethods())
                    .filter((m) -> Modifier.isPublic(m.getModifiers()))
                    .filter((m) -> !Modifier.isStatic(m.getModifiers()))
                    .filter((m) -> !m.isAnnotationPresent(DoNotModel.class))
                    .forEach((m) -> {
                        hkjhjkhkj
                    });

        }

        return builder.buildObjectModels();
    }

    private static Collection<Annotation> getMeta(Class<?> toModel) {
        return Arrays.stream(toModel.getAnnotations())
                .filter((a) -> a.getClass().isAnnotationPresent(Meta.class))
                .collect(Collectors.toList());
    }
}
