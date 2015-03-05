package org.geoint.acetate.reflection;

import java.util.Collection;
import org.geoint.acetate.codec.AcetateCodec;
import org.geoint.acetate.metamodel.FieldAccessor;
import org.geoint.acetate.metamodel.FieldModel;
import org.geoint.acetate.metamodel.FieldSetter;

/**
 *
 */
public class ReflectionFieldModel<D,F,T> implements FieldModel<D,F,T> {

    private ReflectionFieldModel() {
        
    }
    @Override
    public FieldModel<F, ?, ?> getField(String alias) {

    }

    @Override
    public Collection<FieldModel<?, ?, ?>> getFields() {

    }

    @Override
    public FieldAccessor<D, F> getAccessor() {

    }

    @Override
    public FieldSetter<F> getSetter() {

    }

    @Override
    public AcetateCodec<F, T> getCodec() {

    }

    public static class DefaultFieldModelBuilder {
        
    }
}
