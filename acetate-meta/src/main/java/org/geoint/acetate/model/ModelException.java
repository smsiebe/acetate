package org.geoint.acetate.model;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.stream.Stream;
import org.geoint.acetate.model.provider.Resolver;

/**
 *
 * @param <T>
 */
public class ModelException<T extends Throwable> extends ModelElement<Class<T>>
        implements ModelTypeUse<T> {

    private final ModelType<T> baseModel;
    private final ModelMethod decaringModel;
    private final ModelAnnotation[] useAnnotations;

    public ModelException(ModelType<T> baseModel,
            ModelMethod decaringModel,
            ModelAnnotation[] useAnnotations,
            Resolver<Class<T>> elementResolver) {
        super(Stream.concat(Arrays.stream(paramModel.getModelAnnotations()),
                Arrays.stream(useAnnotations))
                .toArray((i) -> new ModelAnnotation[i]),
                baseModel, elementResolver);
        this.baseModel = baseModel;
        this.decaringModel = decaringModel;
        this.useAnnotations = useAnnotations;
    }

    public ModelException(ModelType<T> baseModel,
            ModelMethod decaringModel,
            ModelAnnotation[] useAnnotations,
            Class<T> element) {
        super(Stream.concat(Arrays.stream(paramModel.getModelAnnotations()),
                Arrays.stream(useAnnotations))
                .toArray((i) -> new ModelAnnotation[i]),
                baseModel, element);
        this.baseModel = baseModel;
        this.decaringModel = decaringModel;
        this.useAnnotations = useAnnotations;
    }

    @Override
    public ModelType<T> getType() {
        return this.baseModel;
    }

    @Override
    public ModelAnnotation<?>[] getUseModelAnnotations() {
        return useAnnotations;
    }

    @Override
    public <A extends Annotation> ModelAnnotation<A>[] getModelAnnotations(
            Class<A> modelAnnotationClass) {
        final String annotationName = modelAnnotationClass.getName();
        return Arrays.stream(getModelAnnotations())
                .filter((a) -> a.getAnnotationName().contentEquals(annotationName))
                .toArray((i) -> new ModelAnnotation[i]);
    }

    /**
     * All model annotations defined for this element.
     *
     * @return all model annotations for this element
     */
    @Override
    public ModelAnnotation<?>[] getModelAnnotations() {
        ModelAnnotation<?>[] combined
                = new ModelAnnotation<?>[modelAnnotations.length + useAnnotations.length];
        System.arraycopy(modelAnnotations, 0, combined, 0, modelAnnotations.length);
        System.arraycopy(useAnnotations, 0, combined, modelAnnotations.length, useAnnotations.length);
        return combined;
    }

    /**
     * Model annotations defined directly by this element.
     *
     * @return model annotations declared directly by this element, otherwise
     * return an empty array
     */
    @Override
    public ModelAnnotation<?>[] getDeclaredModelAnnotations() {
        return Arrays.stream(getDeclaredAnnotations())
                .flatMap((a) -> Arrays.stream(modelAnnotations)
                        .filter((ma) -> ma.annotationType()
                                .equals(a.annotationType())))
                .toArray((i) -> new ModelAnnotation[i]);
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        return resolver.resolve().getAnnotation(annotationClass);
    }

    @Override
    public Annotation[] getAnnotations() {
        return resolver.resolve().getAnnotations();
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        return resolver.resolve().getDeclaredAnnotations();
    }

    @Override
    public void visit(ModelVisitor visitor) {
        visitor.visit(this);
    }

}
