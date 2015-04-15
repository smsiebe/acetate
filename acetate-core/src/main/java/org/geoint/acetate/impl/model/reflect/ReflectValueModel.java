package org.geoint.acetate.impl.model.reflect;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import org.geoint.acetate.model.ValueConstraint;
import org.geoint.acetate.model.ValueConstraintException;
import org.geoint.acetate.model.ValueModel;
import org.geoint.acetate.spi.model.DataType;
import org.geoint.acetate.transform.DataFormatException;

/**
 * Reflection-derived value model.
 *
 * @param <T> value type
 */
public class ReflectValueModel<T> implements ValueModel<T> {

    private final String id;
    private final DataType<T> type;
    private final ValueConstraint<T>[] constraints;

    public ReflectValueModel(String id, DataType<T> type,
            ValueConstraint<T>... constraints) {
        this.id = id;
        this.type = type;
        this.constraints = constraints;
    }

    @Override
    public Optional<String> asString(ByteBuffer bb)
            throws DataFormatException {
        return type.asString(bb);
    }

    @Override
    public Optional<String> asString(ByteBuffer bb, int offset, int length)
            throws DataFormatException {
        return type.asString(bb, offset, length);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Collection<ValueConstraint<T>> getConstraints() {
        return Arrays.asList(constraints);
    }

    @Override
    public void validate(T data) throws ValueConstraintException {
        //TODO
        throw new UnsupportedOperationException();
    }

}
