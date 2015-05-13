package org.geoint.acetate.impl.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.geoint.acetate.codec.ObjectCodec;
import org.geoint.acetate.model.ComponentContext;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.constraint.ComponentConstraint;

/**
 * Immutable, thread-safe, component context.
 *
 * @param <T> data type
 */
public final class ImmutableComponentContext<T> implements ComponentContext<T> {

    private final String contextPath;
    private final Set<ComponentConstraint> constraints;
    private final Set<ComponentAttribute> attributes;
    private final ObjectCodec<T> codec;

    private ImmutableComponentContext(
            String contextId,
            Collection<ComponentConstraint> constraints,
            Collection<ComponentAttribute> attributes,
            ObjectCodec<T> codec) {
        this.contextPath = contextId;
        this.constraints = new HashSet<>(constraints);
        this.attributes = new HashSet<>(attributes);
        this.codec = codec;
    }

    /**
     * String which uniquely defines the context of the component within the
     * domain model/runtime environment.
     *
     * @return context path
     */
    public String getContextPath() {
        return contextPath;
    }

    @Override
    public Set<ComponentConstraint> getConstraints() {
        return constraints;
    }

    @Override
    public Set<ComponentAttribute> getAttributes() {
        return attributes;
    }

    @Override
    public ObjectCodec<T> getCodec() {
        return codec;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("context: '")
                .append(contextPath)
                .append("';attributes: ")
                .append(classNameList(attributes))
                .append(";constraints: ")
                .append(classNameList(constraints))
                .append(";codec: '")
                .append(codec.getClass().getName())
                .append("'");
        return sb.toString();
    }

    private String classNameList(Collection<?> types) {
        return types.stream()
                .map((a) -> a.getClass().getName())
                .collect(Collectors.joining(",", "[", "]"));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.contextPath);
        hash = 97 * hash + Objects.hashCode(this.constraints);
        hash = 97 * hash + Objects.hashCode(this.attributes);
        hash = 97 * hash + Objects.hashCode(this.codec);
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
        final ImmutableComponentContext<?> other
                = (ImmutableComponentContext<?>) obj;
        if (!Objects.equals(this.contextPath, other.contextPath)) {
            return false;
        }
        if (!Objects.equals(this.constraints, other.constraints)) {
            return false;
        }
        if (!Objects.equals(this.attributes, other.attributes)) {
            return false;
        }
        return Objects.equals(this.codec, other.codec);
    }

}
