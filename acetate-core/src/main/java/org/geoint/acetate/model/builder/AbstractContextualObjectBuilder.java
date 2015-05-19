package org.geoint.acetate.model.builder;

import org.geoint.acetate.data.transform.BinaryCodec;
import org.geoint.acetate.data.transform.CharacterCodec;
import org.geoint.acetate.impl.model.ImmutableContextPath.ImmutableObjectPath;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.DomainObject;

/**
 *
 * @param <T>
 * @param <B>
 */
public abstract class AbstractContextualObjectBuilder<T, B extends AbstractContextualObjectBuilder>
        extends AbstractContextualComponentBuilder<T, B> {

    public AbstractContextualObjectBuilder(ImmutableObjectPath path,
            String baseComponentName, boolean isCollection) {
        super(path, baseComponentName, isCollection);
    }

    /**
     * Explicitly sets a binary codec to use to convert to/from an object in
     * this context.
     *
     * @param codec
     * @return this builder (fluid interface)
     */
    @Override
    public B codec(BinaryCodec<T> codec) {
        super.codec(codec);
        return self();
    }

    /**
     * Explicitly sets a character codec to use to convert to/from an object in
     * this context.
     *
     * @param codec
     * @return this builder (fluid interface)
     */
    @Override
    public B codec(CharacterCodec<T> codec) {
        super.codec(codec);
        return self();
    }

    @Override
    abstract public DomainObject<T> build(DomainModel model);
}
