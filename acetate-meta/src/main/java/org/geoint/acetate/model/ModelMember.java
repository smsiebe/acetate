package org.geoint.acetate.model;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import org.geoint.acetate.model.provider.Resolver;

/**
 * A member of a {@link ModelType}.
 *
 * @param <E>
 */
public abstract class ModelMember<E extends AnnotatedElement>
        extends ModelElement<E> implements Member {

    private final ModelType<?> declaringModel;
    private final String name;

    public ModelMember(ModelType<?> declaringModel,
            String name,
            ModelAnnotation<?>[] modelAnnotations,
            Resolver<E> elementResolver) {
        super(modelAnnotations, elementResolver);
        this.declaringModel = declaringModel;
        this.name = name;
    }

    public ModelMember(ModelType<?> declaringModel,
            String name,
            ModelAnnotation<?>[] modelAnnotations,
            E element) {
        super(modelAnnotations, element);
        this.declaringModel = declaringModel;
        this.name = name;
    }

    /**
     * The model of the type this member was declared.
     *
     * @return model of the declaring type
     */
    public ModelType<?> getDeclaringModel() {
        return declaringModel;
    }

    /**
     * Returns an instance of the class this model member is defined.
     *
     * @return declaring class type
     */
    @Override
    public Class<?> getDeclaringClass() {
        return declaringModel.getType();
    }

    @Override
    public String getName() {
        return name;
    }

}
