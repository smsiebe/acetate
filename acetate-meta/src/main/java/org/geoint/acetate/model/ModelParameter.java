package org.geoint.acetate.model;

import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.stream.Stream;
import org.geoint.acetate.model.provider.Resolver;

/**
 * Model method parameter.
 *
 * @param <T>
 */
public class ModelParameter<T> extends ModelElement<Parameter>
        implements ModelTypeUse {

    private final ModelMethod declaringMethod;
    private final String paramName;
    private final ModelType<T> paramModel;
    private final ModelAnnotation[] useAnnotations;

    public ModelParameter(ModelMethod declaringMethod,
            String paramName,
            ModelType<T> paramModel,
            ModelAnnotation[] useAnnotations,
            Resolver<Parameter> elementResolver) {
        super(Stream.concat(Arrays.stream(paramModel.getModelAnnotations()),
                Arrays.stream(useAnnotations))
                .toArray((i) -> new ModelAnnotation[i]),
                elementResolver);
        this.declaringMethod = declaringMethod;
        this.paramName = paramName;
        this.paramModel = paramModel;
        this.useAnnotations = useAnnotations;
    }

    public ModelParameter(ModelMethod declaringMethod,
            String paramName,
            ModelType<T> paramModel,
            ModelAnnotation[] useAnnotations,
            Parameter element) {
        super(Stream.concat(Arrays.stream(paramModel.getModelAnnotations()),
                Arrays.stream(useAnnotations))
                .toArray((i) -> new ModelAnnotation[i]),
                element);
        this.declaringMethod = declaringMethod;
        this.paramName = paramName;
        this.paramModel = paramModel;
        this.useAnnotations = useAnnotations;
    }

    @Override
    public ModelType getType() {
        return this.paramModel;
    }

    /**
     * Name of the method parameter.
     *
     * @return method parameter name
     */
    public String getName() {
        return paramName;
    }

    @Override
    public ModelAnnotation[] getUseModelAnnotations() {
        return useAnnotations;
    }

    @Override
    public void visit(ModelVisitor visitor) {
        visitor.visit(this);
    }

}
