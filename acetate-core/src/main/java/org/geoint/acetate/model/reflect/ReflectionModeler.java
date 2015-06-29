package org.geoint.acetate.model.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.geoint.acetate.impl.meta.model.DuplicateParametersException;
import org.geoint.acetate.impl.meta.model.ModelBuilder;
import org.geoint.acetate.impl.meta.model.ObjectModelBuilder;
import org.geoint.acetate.impl.meta.model.OperationModelBuilder;
import org.geoint.acetate.meta.annotation.DoNotModel;
import org.geoint.acetate.meta.annotation.Domain;
import org.geoint.acetate.meta.annotation.MetaAttribute;
import org.geoint.acetate.meta.annotation.Operation;
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
    public static Collection<ObjectModel<?>> model(Class<?>... classes)
            throws ModelException {

        ModelBuilder builder = new ModelBuilder();

        final Set<Class<?>> modeledClasses
                = new HashSet<>(Arrays.asList(classes));
        final Queue<Class<?>> modelQueue = new LinkedList<>(modeledClasses);

        while (!modelQueue.isEmpty()) {
            final Class<?> toModel = modelQueue.poll();
            if (toModel.isAnnotationPresent(DoNotModel.class)) {
                //explicitly declared to not model this class
                logger.fine(() -> "Modeling of class "
                        + toModel.getClass().getName()
                        + " skipped, annotated with DoNotModel");
                continue;
            }
            //check if this class has annotated with a Domain annotation, 
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
            final ObjectModelBuilder<?> ob = builder.forClass(toModel);

            //add MetaAttributes annotated on any Domain annotation to 
            //the object model
            for (Annotation a : objMetaAnnotations) {
                for (Method m : a.getClass().getDeclaredMethods()) {
                    try {
                        if (!m.isAnnotationPresent(MetaAttribute.class)) {
                            //not a meta attribute, skip annotation method
                            continue;
                        }
                        MetaAttribute ma = m.getAnnotation(MetaAttribute.class);
                        ob.withAttribute((!ma.name().isEmpty()
                                        ? ma.name()
                                        : a.getClass().getAnnotation(Domain.class).name()
                                        + "." + m.getName()),
                                String.valueOf(m.invoke(a)));
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        throw new ReflectionModelException(
                                "Unable to resolve meta attribute value while "
                                + "modeling " + toModel.getName(), ex);
                    }
                }
            }

            //add inheritence, including all interfaces and parent class, 
            //if appliable
            Set<Class<?>> parents = new HashSet();
            parents.addAll(Arrays.asList(toModel.getInterfaces()));

            if (!toModel.isInterface()) {
                final Class<?> superclass = toModel.getSuperclass();
                if (!superclass.equals(Object.class)) { //skip Object class
                    parents.add(superclass);
                }
            }

            parents.stream()
                    .filter((p) -> !modeledClasses.contains(p))
                    .forEach((p) -> {
                        modeledClasses.add(p);
                        modelQueue.add(p);
                    });

            //add operation models only declared by this class, not for 
            //parent models (this will be added by the builder)
            Arrays.stream(toModel.getDeclaredMethods())
                    .filter((m) -> Modifier.isPublic(m.getModifiers()))
                    .filter((m) -> !Modifier.isStatic(m.getModifiers()))
                    .filter((m) -> !m.isAnnotationPresent(DoNotModel.class))
                    .forEach((m) -> {

                        OperationModelBuilder opb = ob.withOperation(
                                getOperationName(m));
                        opb.withDescription(getOperationDescription(m));

                        //parameters
                        for (Parameter p : m.getParameters()) {
                            try {
                                opb.withParameter(p.getName(), p.getType());
                            } catch (DuplicateParametersException ex) {
                                //we won't get duplicates here using reflection, 
                                //compilers will prevent this
                                assert false : "Unexpected parameter name "
                                + "collision when reflecting on method '"
                                + toModel.getName() + "#" + m.getName();
                            }
                        }

                        //return
                        final Class<?> returnType = m.getReturnType();
                        if (!returnType.equals(Void.class)) {
                            opb.withReturn(returnType);
                        }

                        //possible exceptions
                        Arrays.stream(m.getExceptionTypes())
                        .forEach((e) -> opb.withException(e));
                    });
        }
        return builder.buildObjectModels();
    }

    private static String getOperationDescription(Method m) {
        if (m.isAnnotationPresent(Operation.class)) {
            final String desc = m.getAnnotation(Operation.class).description();
            if (!desc.isEmpty()) {
                return desc;
            }
        }

        //use default operation nameing
        return null;
    }

    private static String getOperationName(Method m) {
        if (m.isAnnotationPresent(Operation.class)) {
            final String name = m.getAnnotation(Operation.class).name();
            if (!name.isEmpty()) {
                return name;
            }
        }

        //use default operation nameing
        return m.getName();
    }

    private static Collection<Annotation> getMeta(Class<?> toModel) {
        return Arrays.stream(toModel.getAnnotations())
                .filter((a) -> a.getClass().isAnnotationPresent(Domain.class))
                .collect(Collectors.toList());
    }
}
