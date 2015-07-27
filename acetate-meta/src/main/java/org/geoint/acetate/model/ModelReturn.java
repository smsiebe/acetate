package org.geoint.acetate.model;

import java.util.Arrays;
import java.util.stream.Stream;
import org.geoint.acetate.model.provider.Resolver;

/**
 *
 */
public class ModelReturn<T> extends ModelElement<Class<T>>
        implements ModelTypeUse<T> {

    private final ModelType<T> baseModel;
    private final ModelMethod decaringModel;
    private final ModelAnnotation[] useAnnotations;

    public ModelReturn(ModelType<T> baseModel,
            ModelMethod decaringModel,
            ModelAnnotation[] useAnnotations,
            Resolver<Class<T>> elementResolver) {
        super(Stream.concat(Arrays.stream(baseModel.getModelAnnotations()),
                Arrays.stream(useAnnotations))
                .toArray((i) -> new ModelAnnotation[i]),
                elementResolver);
        this.baseModel = baseModel;
        this.decaringModel = decaringModel;
        this.useAnnotations = useAnnotations;
    }

    public ModelReturn(ModelType<T> baseModel,
            ModelMethod decaringModel,
            ModelAnnotation[] useAnnotations,
            Class<T> element) {
        super(Stream.concat(Arrays.stream(baseModel.getModelAnnotations()),
                Arrays.stream(useAnnotations))
                .toArray((i) -> new ModelAnnotation[i]),
                element);
        this.baseModel = baseModel;
        this.decaringModel = decaringModel;
        this.useAnnotations = useAnnotations;
    }

    @Override
    public ModelAnnotation<?>[] getUseModelAnnotations() {
        return useAnnotations;
    }

    @Override
    public ModelType<T> getType() {
        return this.baseModel;
    }

    @Override
    public void visit(ModelVisitor visitor) {
        visitor.visit(this);
    }

}
