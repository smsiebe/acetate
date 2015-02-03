package org.geoint.acetate.impl.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import org.geoint.acetate.metamodel.FieldAccessor;
import org.geoint.acetate.metamodel.FieldModel;
import org.geoint.acetate.metamodel.FieldSetter;

/**
 * Simple field model that delegates to the accessor/setter.
 *
 * @param <P> object type containing this field
 * @param <F> field value type
 */
public class DefaultFieldModel<P, F> implements FieldModel<P, F> {

    private final String[] aliases;
    private final FieldAccessor<P, F> accessor;
    private final FieldSetter<P, F> setter;

    public DefaultFieldModel(FieldAccessor<P, F> accessor,
            FieldSetter<P, F> setter, String... aliases) {
        this.accessor = accessor;
        this.setter = setter;
        this.aliases = aliases;
    }

    public static <P, F> DefaultFieldModel<P, F> addAliases(
            DefaultFieldModel<P, F> model,
            String... newAliases) {
        String[] aliases = new String[model.aliases.length + newAliases.length];
        System.arraycopy(model.aliases, 0, aliases, 0, model.aliases.length);
        System.arraycopy(newAliases, 0, aliases, model.aliases.length, newAliases.length);
        return new DefaultFieldModel(model.accessor, model.setter, aliases);
    }

    @Override
    public Collection<String> getNames() {
        return Arrays.stream(aliases).collect(Collectors.toList());
    }

    @Override
    public F getValue(P container) {
        return accessor.get(container);
    }

    @Override
    public void setValue(P container, F value) {
        setter.set(container, value);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Arrays.deepHashCode(this.aliases);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DefaultFieldModel<?, ?> other = (DefaultFieldModel<?, ?>) obj;
        if (!Arrays.deepEquals(this.aliases, other.aliases)) {
            return false;
        }
        return true;
    }

}
