package org.geoint.acetate.model;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.stream.Stream;
import org.geoint.acetate.model.provider.Resolver;

/**
 * Model type (class/interface) definition.
 *
 * @param <T> type
 */
public class ModelType<T> extends ModelElement<Class<T>> {

    private final String typeName;
    private final ModelMethod[] methods;
    private final ModelField[] fields;
    private final ModelType<? extends T>[] subclasses;

    public ModelType(String typeName,
            Resolver<Class<T>> elementResolver,
            ModelAnnotation<?>[] modelAnnotations,
            ModelMethod[] methods,
            ModelField[] fields,
            ModelType<? extends T>[] subclasses) {
        super(modelAnnotations, elementResolver);
        this.typeName = typeName;
        this.methods = methods;
        this.fields = fields;
        this.subclasses = subclasses;
    }

    public ModelType(String typeName,
            Class<T> elementResolver,
            ModelAnnotation<?>[] modelAnnotations,
            ModelMethod[] methods,
            ModelField[] fields,
            ModelType<? extends T>[] subclasses) {
        super(modelAnnotations, () -> elementResolver);
        this.typeName = typeName;
        this.methods = methods;
        this.fields = fields;
        this.subclasses = subclasses;
    }

    /**
     * All methods that are defined with any model annotation.
     *
     * @return methods which are defined with a model annotation
     */
    public ModelMethod[] getModelMethods() {
        return Arrays.copyOf(methods, methods.length);
    }

    /**
     * All methods annotated with the specified model annotation.
     *
     * @param modelAnnotation
     * @return methods defined with the specified model annotation or empty
     * array
     */
    public ModelMethod[] getModelMethods(Class<? extends Annotation> modelAnnotation) {
        return Arrays.stream(methods)
                .filter((m) -> m.isAnnotationPresent(modelAnnotation))
                .toArray((i) -> new ModelMethod[i]);
    }

    /**
     * All fields annotated with any model annotation.
     *
     * @return fields annotated with a model annotation or an empty array
     */
    public ModelField<?>[] getModelFields() {
        return Arrays.copyOf(fields, fields.length);
    }

    /**
     * All fields annotated with the specified model annotation.
     *
     * @param modelAnnotation
     * @return fields annotated with the specified model annotation or empty
     * array
     */
    public ModelField<?>[] getModelFields(
            Class<? extends Annotation> modelAnnotation) {
        return Arrays.stream(methods)
                .filter((m) -> m.isAnnotationPresent(modelAnnotation))
                .toArray((i) -> new ModelField[i]);
    }

    /**
     * All members of this type that are annotated with at least one metamodel
     * annotation.
     *
     * @return all members that are annotated with a metamodel annotation or
     * empty array
     */
    public ModelMember[] getModelMembers() {
        ModelMember[] members = new ModelMember[methods.length + fields.length];
        System.arraycopy(methods, 0, members, 0, methods.length);
        System.arraycopy(fields, 0, members, methods.length, fields.length);
        return members;
    }

    /**
     * All members of this type which are annotated with the specified model
     * annotation.
     *
     * @param modelAnnotation
     * @return all member models annotated with this model annotation or an
     * empty array
     */
    public ModelMember[] getModelMembers(Class<? extends Annotation> modelAnnotation) {
        return Stream.concat(Arrays.stream(methods), Arrays.stream(fields))
                .filter((m) -> m.isAnnotationPresent(modelAnnotation))
                .toArray((i) -> new ModelMember[i]);
    }

    /**
     * ModelProvider provided Class of this model type.
     *
     * @return class of the model type
     */
    public Class<?> getType() {
        return resolver.resolve();
    }

    /**
     * Class name of the model type.
     *
     * @return model type class name
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * Subclasses of the model type.
     *
     * @return subclasses of the model type
     */
    public ModelType<? extends T>[] getSubclasses() {
        return subclasses;
    }

    @Override
    public void visit(ModelVisitor visitor) {
        throw new UnsupportedOperationException();
    }
}
