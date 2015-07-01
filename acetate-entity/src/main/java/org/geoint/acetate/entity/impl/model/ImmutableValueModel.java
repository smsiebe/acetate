package org.geoint.acetate.entity.impl.model;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Collection;
import org.geoint.acetate.bind.transform.BufferedCodec;
import org.geoint.acetate.entity.model.ModelVisitor;
import org.geoint.acetate.impl.meta.model.DomainId;
import org.geoint.acetate.entity.model.ValueModel;
import org.geoint.acetate.entity.attic.attribute.ModelAttribute;
import org.geoint.acetate.entity.attic.constraint.DataConstraint;

/**
 *
 * @param <T>
 */
public final class ImmutableValueModel<T> extends ImmutableDataModel<T>
        implements ValueModel<T> {

    private final BufferedCodec<T, CharBuffer> charCodec;
    private final BufferedCodec<T, ByteBuffer> binCodec;

    public ImmutableValueModel(DomainId domainId, Class<T> dataType,
            String name, String description,
            Collection<ModelAttribute> attributes,
            Collection<DataConstraint> constraints,
            BufferedCodec<T, CharBuffer> charCodec,
            BufferedCodec<T, ByteBuffer> binCodec) {
        super(domainId, dataType, name, description, attributes, constraints);
        this.charCodec = charCodec;
        this.binCodec = binCodec;
    }

    @Override
    public BufferedCodec<T, CharBuffer> getCharacterCodec() {
        return charCodec;
    }

    @Override
    public BufferedCodec<T, ByteBuffer> getBinaryCodec() {
        return binCodec;
    }

    @Override
    public void visit(ModelVisitor visitor) {
        visitor.value(this);
    }
}
