package org.geoint.acetate.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;
import org.geoint.acetate.model.provider.Resolver;

/**
 *
 * @param <T>
 */
public class ModelField<T> extends ModelMember<Field> implements ModelTypeUse {

    private final ModelType<T> fieldModel;
    private final ModelAnnotation<?>[] useAnnotations;

    public ModelField(
            ModelType<?> declaringType,
            String name,
            ModelType<T> fieldModel,
            ModelAnnotation<?>[] useAnnotations,
            Resolver<Field> elementResolver) {
        super(declaringType, name,
                Stream.concat(Arrays.stream(fieldModel.getModelAnnotations()),
                        Arrays.stream(useAnnotations))
                .toArray((i) -> new ModelAnnotation[i]),
                elementResolver);
        this.fieldModel = fieldModel;
        this.useAnnotations = useAnnotations;
    }

    public ModelField(
            ModelType<?> declaringType,
            String name,
            ModelType<T> fieldModel,
            ModelAnnotation<?>[] useAnnotations,
            Field field) {
        this(declaringType, name, fieldModel, useAnnotations, () -> field);
    }

    @Override
    public ModelAnnotation<?>[] getUseModelAnnotations() {
        return useAnnotations;
    }

    public Field getField() {
        return resolver.resolve();
    }

    @Override
    public int getModifiers() {
        return resolver.resolve().getModifiers();
    }

    @Override
    public boolean isSynthetic() {
        return resolver.resolve().isSynthetic();
    }

    @Override
    public ModelType getType() {
        return this.fieldModel;
    }

    @Override
    public void visit(ModelVisitor visitor) {
        visitor.visit(this);
    }

}
