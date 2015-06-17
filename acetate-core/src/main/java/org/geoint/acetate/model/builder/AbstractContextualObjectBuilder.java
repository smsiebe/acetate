package org.geoint.acetate.model.builder;

import org.geoint.acetate.bind.transform.BufferedCodec;
import org.geoint.acetate.bind.transform.CharacterCodec;
import org.geoint.acetate.impl.model.ImmutableObjectAddress.ImmutableComponentAddress;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.ObjectModel;

/**
 *
 * @param <T>
 * @param <B>
 */
public abstract class AbstractContextualObjectBuilder<T, B extends AbstractContextualObjectBuilder<T, B>>
        extends AbstractContextualComponentBuilder<T, B> {

    protected final String baseComponentName;
    protected final boolean isCollection;
    protected BufferedCodec<T> binaryCodec;
    protected CharacterCodec<T> charCodec;

    public AbstractContextualObjectBuilder(ImmutableComponentAddress path,
            String baseComponentName, boolean isCollection) {
        super(path);
        this.baseComponentName = baseComponentName;
        this.isCollection = isCollection;
    }

    /**
     * Explicitly sets a binary codec to use to convert to/from an object in
     * this context.
     *
     * @param codec
     * @return this builder (fluid interface)
     */
    public B codec(BufferedCodec<T> codec) {
        binaryCodec = codec;
        return self();
    }

    /**
     * Explicitly sets a character codec to use to convert to/from an object in
     * this context.
     *
     * @param codec
     * @return this builder (fluid interface)
     */
    public B codec(CharacterCodec<T> codec) {
        charCodec = codec;
        return self();
    }

    @Override
    abstract public ObjectModel<T> build(DomainModel model);
}
