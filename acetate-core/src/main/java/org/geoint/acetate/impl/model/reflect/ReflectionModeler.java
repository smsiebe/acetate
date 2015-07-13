package org.geoint.acetate.impl.model.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.geoint.acetate.model.annotation.DoNotModel;
import org.geoint.acetate.model.annotation.ModelName;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.ObjectModel;
import org.geoint.acetate.impl.domain.model.DomainBuilder;
import org.geoint.acetate.impl.domain.model.DomainId;
import org.geoint.acetate.impl.domain.model.DuplicateParametersException;
import org.geoint.acetate.impl.domain.model.MetaVersionImpl;
import org.geoint.acetate.impl.domain.model.ObjectId;
import org.geoint.acetate.impl.domain.model.ObjectModelBuilder;
import org.geoint.acetate.impl.domain.model.OperationModelBuilder;
import org.geoint.acetate.impl.domain.model.UnknownDomainObjectException;
import org.geoint.acetate.meta.ModelException;
import org.geoint.acetate.meta.annotation.MetaObject;

/**
 * Reflects on the provided classes to create domain model(s) based on
 * annotations.
 *
 */
public class ReflectionModeler implements Callable<Collection<DomainModel>> {

    private DomainBuilder builder;
    private final Class<?>[] baseClasses; //classes the modeler started with
    private final Map<Class<?>, ObjectId> classIds = new HashMap<>();
    private final Queue<Class<?>> toModelQueue = new LinkedList<>();

    private static final Logger logger
            = Logger.getLogger(ReflectionModeler.class.getName());
    private static final char CHAR_PERIOD = '.';

    private ReflectionModeler(Class<?>... classes)
            throws ModelException {
        baseClasses = classes;
    }

    /**
     * Synchronously (on requesting thread) create object models for the
     * specified classes.
     *
     * @param classes
     * @return object models
     * @throws ModelException thrown if there is an error in modeling
     */
    public static Collection<DomainModel> model(Class<?>... classes)
            throws ModelException {
        ReflectionModeler modeler = new ReflectionModeler(classes);
        return modeler.call();
    }

    /**
     * Synchronously (on requesting thread) create object models from the array
     * of class names by loading the classes using the context classloader.
     *
     * @param classNames
     * @return modeler
     * @throws ModelException thrown if there is an error in modeling
     */
    public static Collection<DomainModel> model(String... classNames)
            throws ModelException {
        return model(Thread.currentThread().getContextClassLoader(),
                classNames);
    }

    /**
     * Synchronously (on requesting thread) create the object model from the
     * array of class names using the specified classloader.
     *
     * @param cl
     * @param classNames
     * @return modeler
     * @throws ModelException thrown if there is an error in modeling
     */
    public static Collection<DomainModel> model(ClassLoader cl,
            String... classNames) throws ModelException {
        Class[] classes = new Class[classNames.length];
        for (int i = 0; i < classNames.length; i++) {
            try {
                classes[i] = cl.loadClass(classNames[i]);
            } catch (ClassNotFoundException ex) {
                //should we throw exception instead???
                throw new UnknownDomainObjectException(null,
                        "Context classloader could not load class '"
                        + classNames[i]
                        + "'; modeling of this class has been "
                        + "skipped.", ex);
            }
        }
        return model(classes);
    }

    /**
     * Created object models using reflection.
     *
     * @return metamodels discovered or an empty collection
     * @throws ModelException thrown if there is an error in modeling
     */
    @Override
    public Collection<DomainModel> call() throws ModelException {
        builder = new DomainBuilder();

        //first we'll seed the toModelQueue with the base classes
        for (Class<?> c : baseClasses) {
            if (!canModel(c)) {
                logger.fine(() -> "Skipping base class as it was annotated "
                        + "explicitly as not a model object.");
            } else {
                getObjectBuilder(c);
            }
        }

        //now we drain the model queue, modeling each of the classes.  as we 
        //model, more classes may be added to the model queue (ie from 
        //composite relationships, operation parameter/response types, etc)
        while (!toModelQueue.isEmpty()) {

            final Class<?> toModel = toModelQueue.poll();

            logger.fine(() -> "Creating model of " + toModel.getName()
                    + " with reflection.");

            final ObjectModelBuilder ob = getObjectBuilder(toModel);
            ob.specializes(getParentIds(toModel));

            //add operations only declared by this class, not inheited, as 
            //operation inheritence is ensured by the builder
            Arrays.stream(toModel.getDeclaredMethods())
                    .filter((m) -> Modifier.isPublic(m.getModifiers()))
                    .filter((m) -> !Modifier.isStatic(m.getModifiers()))
                    .filter((m) -> !m.isAnnotationPresent(DoNotModel.class))
                    .filter((m) -> !m.isBridge())
                    .filter((m) -> !m.isSynthetic())
                    .forEach((m) -> {
                        final String operationName = getOperationName(m);

                        try {

                            //return object id or null if void return type
                            final Class<?> returnType = m.getReturnType();
                            if (!canModel(returnType)) {
                                logger.fine(() -> String.format("Method '%1$s' "
                                                + "is not eligable as an operation; "
                                                + "return type '%2$s' cannot be modeled.",
                                                m.toString(), returnType.getName()));
                                return;
                            }
                            final ObjectId returnId = (returnType.equals(Void.class))
                                    ? null
                                    : getObjectBuilder(returnType).getObjectId();

                            //exception models
                            final List<ObjectId> exceptionIds = new ArrayList<>();
                            for (Class<?> e : m.getExceptionTypes()) {
                                if (!canModel(e)) {
                                    logger.fine(() -> String.format("Method '%1$s' "
                                                    + "is not eligable as an operation; "
                                                    + "exception type '%2$s' cannot be modeled.",
                                                    m.toString(), returnType.getName()));
                                    return;
                                }
                                exceptionIds.add(
                                        getObjectBuilder(e).getObjectId());
                            }

                            //model operation components first, then create 
                            //builder, as it self-registers 
                            final Map<String, ObjectId> params = new HashMap<>();
                            for (Parameter p : m.getParameters()) {
                                //TODO track contextual metamodel annotations
                                Class<?> paramType = p.getType().getClass();
                                if (!canModel(paramType)) {
                                    logger.fine(() -> String.format("Method '%1$s' "
                                                    + "is not eligable as an operation; "
                                                    + "parameter type '%2$s' cannot be modeled.",
                                                    m.toString(), returnType.getName()));
                                    return;
                                }
                                params.put(generateParamName(p),
                                        getObjectBuilder(paramType).getObjectId()
                                );
                            }

                            OperationModelBuilder opb = ob.withOperation(
                                    operationName);
                            opb.withDescription(getOperationDescription(m));
                            params.entrySet().forEach((e) -> {
                                try {
                                    opb.withParameter(
                                            e.getKey(),
                                            e.getValue()
                                    );
                                } catch (DuplicateParametersException ex) {
                                    //this won't be thrown since the compiler 
                                    //will prevent this from happening
                                }
                            });
                            if (returnId != null) {
                                opb.withReturn(returnId);
                            }
                            exceptionIds.forEach((eid) -> opb.withException(eid));

                        } catch (ModelException ex) {
                            logger.log(Level.WARNING, "Operation '"
                                    + operationName
                                    + "' will not be included in the object model '"
                                    + ob.getObjectId().asString()
                                    + "'", ex);
                        }
                    });
        }
        return builder.build();
//        return builder.build().stream()
//                .flatMap((d) -> d.findAll().stream())
//                .collect(Collectors.toList());
    }

    private String generateParamName(Parameter p) {
        //TODO add annotation overriding parameter name

        return p.getName();
    }

    /**
     * Retrieves the object builder for the requested class, creating the
     * builder if necessary.
     *
     * @param toModel class to model
     * @return object model builder for the class
     * @throws ModelException thrown if there was a problem gathering domain
     * model context through reflection
     */
    private ObjectModelBuilder getObjectBuilder(Class<?> toModel)
            throws ModelException {
        synchronized (toModel) {

            if (classIds.containsKey(toModel)) {
                //already (at least partially) modeled
                return builder.forObject(classIds.get(toModel));
            }

            //unknown class, create the builder and register in model queue
            //to complete modeling
            final Collection<Annotation> objMetaAnnotations
                    = getMetamodelAnnotations(toModel);

            //discover the metamodel attributes first, which is used to determine 
            //the domain context of the object
            Map<String, String> metamodelAttributes
                    = extractMetamodelAttributes(objMetaAnnotations);

            //Set/add the default metamodel attribute values for required 
            //attributes if they are not already set
            setDefaultIfMissing(metamodelAttributes,
                    DomainModel.META_DOMAIN_NAME,
                    DomainId.DEFAULT_DOMAIN::getName);

            setDefaultIfMissing(metamodelAttributes,
                    DomainModel.META_DOMAIN_VERSION,
                    DomainId.DEFAULT_DOMAIN.getVersion()::asString);

            setDefaultIfMissing(metamodelAttributes,
                    ObjectModel.META_OBJECT_NAME,
                    () -> classNameToCamelCase(toModel)
            );

            final ObjectId objectId = ObjectId.getInstance(
                    metamodelAttributes.get(DomainModel.META_DOMAIN_NAME),
                    MetaVersionImpl.valueOf(metamodelAttributes
                            .get(DomainModel.META_DOMAIN_VERSION)
                    ),
                    metamodelAttributes.get(ObjectModel.META_OBJECT_NAME));

            final ObjectModelBuilder obp = builder.forObject(objectId);
            obp.withAttributes(metamodelAttributes);
            this.classIds.put(toModel, objectId);
            this.toModelQueue.add(toModel); //only partially modeled, register to complete
            return obp;
        }
    }

    private ObjectId[] getParentIds(Class<?> clazz) throws ModelException {

        List<AnnotatedType> parentTypes = new ArrayList<>();
        //interfaces
        Collections.addAll(parentTypes, clazz.getAnnotatedInterfaces());
        //parent class (if applicable, see Class#getAnnotatedSuperclass)
        AnnotatedType superclass = clazz.getAnnotatedSuperclass();
        if (superclass != null) {
            parentTypes.add(superclass);
        }

        Set<ObjectId> parentIds = new HashSet<>();
        for (AnnotatedType at : parentTypes) {

            if (!canModel(at)) {
                logger.fine(() -> "Parent class '"
                        + at.getType().getTypeName()
                        + "' is not to be modeled in the context of '"
                        + classIds.get(clazz).asString()
                        + "'");
                continue;
            }

            final Class<?> parentClass = at.getType().getClass();
            ObjectModelBuilder parentBuilder
                    = getObjectBuilder(parentClass);
            parentIds.add(parentBuilder.getObjectId());
        }
        return parentIds.toArray(new ObjectId[parentIds.size()]);
    }

    /**
     * Converts a class name to a camel-cased version, removing the special
     * characters.
     *
     * @param clazz
     * @return camel-cased class name
     */
    private String classNameToCamelCase(Class<?> clazz) {
        boolean nextUpper = false;
        StringBuilder sb = new StringBuilder();
        for (char c : clazz.getName().toCharArray()) {
            if (c == CHAR_PERIOD) {
                nextUpper = true;
                //skip period
            } else {
                if (nextUpper) {
                    nextUpper = false;
                    sb.append(Character.toUpperCase(c));
                } else {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    private void setDefaultIfMissing(
            Map<String, String> metamodelAttributes,
            final String attributeName,
            Supplier<String> valueSupplier) {
        final String value = metamodelAttributes.get(attributeName);
        if (value == null || value.isEmpty()) {
            metamodelAttributes.put(attributeName, valueSupplier.get());
        }
    }

    /**
     * Extract the metamodel attributes from the metamodel annotations.
     *
     * @param metamodelAnnotations
     * @return metamodel attributes found on annotations
     * @throws ReflectionModelException thrown if there was a reflection-related
     * problem that prevented the model from being built, which will be
     * explained in the cause-by
     */
    private Map<String, String> extractMetamodelAttributes(
            Collection<Annotation> metamodelAnnotations)
            throws ReflectionModelException {

        Map<String, String> metamodelAttributes = new ConcurrentHashMap<>();

        //unable to use lambdas...scoping and exception handling issues...
        //don't bother trying to make this pretty =)
        for (final Annotation a : metamodelAnnotations) {
            for (Method m : a.annotationType().getDeclaredMethods()) {
                try {
                    if (!isMetamodelAttribute(m)) {
                        //not a meta attribute, skip annotation method
                        continue;
                    }
                    Meta ma = m.getDeclaredAnnotation(Meta.class);

                    Object attributeValue = m.invoke(a);
                    final String attributeValueString = (attributeValue == null)
                            ? null
                            : String.valueOf(attributeValue);

                    metamodelAttributes.put((!ma.name().isEmpty()
                                    ? ma.name() //use the method name if the Meta#name() is default
                                    : a.getClass().getDeclaredAnnotation(MetaObject.class).name()
                                    + "." + m.getName()),//Meta#name() overrides the attribute name
                            attributeValueString);
                } catch (IllegalAccessException | IllegalArgumentException |
                        InvocationTargetException ex) {
                    throw new ReflectionModelException(
                            "Unable to resolve meta attribute value while "
                            + "modeling " + a.annotationType().getName(), ex);
                }
            }
        }

        return metamodelAttributes;
    }

    /**
     * Checks if the (annotation) method is annotated with {@link Meta}.
     *
     * Intentionally defined implementing the {@link Predicate} interface.
     *
     * @param m
     * @return true if its a metamodel attribute
     */
    private boolean isMetamodelAttribute(Method m) {
        return m.isAnnotationPresent(Meta.class);
    }

    /**
     * Determine if the class represents a domain model object.
     *
     * Intentionally defined implementing the {@link Predicate} interface.
     *
     * @param clazz
     * @return true if it is a domain model object
     */
    private boolean canModel(AnnotatedElement annotated) {
        return (!annotated.isAnnotationPresent(DoNotModel.class))
                && hasMetamodelAnnotations(annotated);
    }

    /**
     * Retrieve the operation description or null if not defined.
     *
     *
     * Intentionally defined implementing the {@link Function} interface.
     *
     * @param m
     * @return operation description or null if not set
     */
    private String getOperationDescription(Method m) {
        if (m.isAnnotationPresent(ModelName.class)) {
            final String desc = m.getDeclaredAnnotation(ModelName.class)
                    .description();
            if (!desc.isEmpty()) {
                return desc;
            }
        }

        //use default operation nameing
        return null;
    }

    /**
     * Determines the operation name based on the presence of a
     * {@link ModelName} annotation, or returns the a name based on the default
     * operation naming convention.
     *
     *
     * Intentionally defined implementing the {@link Function} interface.
     *
     * @param m
     * @return operation name for method
     */
    private String getOperationName(Method m) {
        if (m.isAnnotationPresent(ModelName.class)) {
            final String name = m.getDeclaredAnnotation(ModelName.class).name();
            if (!name.isEmpty()) {
                return name;
            }
        }

        //use default operation naming...shockingly, the method name
        return m.getName();
    }

    /**
     * Check if this class has annotated with a MetaObject annotation, otherwise
 return null because this object is not part of a model


 Intentionally defined implementing the {@link Function} interface.
     *
     * @param element
     * @return metamodel annotations for class
     */
    private Collection<Annotation> getMetamodelAnnotations(AnnotatedElement element) {
        return Arrays.stream(element.getDeclaredAnnotations())
                .filter(this::isMetamodelAnnotation)
                .collect(Collectors.toList());
    }

    private boolean hasMetamodelAnnotations(AnnotatedElement element) {
        return Arrays.stream(element.getDeclaredAnnotations())
                .anyMatch(this::isMetamodelAnnotation);
    }

    private boolean isMetamodelAnnotation(Annotation a) {
        return a.annotationType().isAnnotationPresent(MetaObject.class);
    }
}
