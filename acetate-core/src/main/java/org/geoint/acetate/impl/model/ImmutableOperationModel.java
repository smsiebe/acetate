package org.geoint.acetate.impl.model;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import org.geoint.acetate.model.ModelContextPath;
import org.geoint.acetate.model.ContextualComponent;
import org.geoint.acetate.model.OperationModel;
import org.geoint.acetate.model.attribute.ComponentAttribute;

/**
 *
 */
public class ImmutableOperationModel implements OperationModel {

    private final ModelContextPath path;
    private final String name;
    private final Optional<String> description;
    private final Optional<ContextualComponent> returned;
    private final Collection<? extends ContextualComponent> params;
    private final Collection<? extends ComponentAttribute> attributes;

    ImmutableOperationModel(ModelContextPath path, String name,
            Optional<String> description,
            Optional<ContextualComponent> returned,
            Collection<? extends ContextualComponent> params,
            Collection<? extends ComponentAttribute> attributes) {
        this.path = path;
        this.name = name;
        this.description = description;
        this.returned = returned;
        this.params = params;
        this.attributes = attributes;
    }

    @Override
    public ModelContextPath getPath() {
        return path;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Optional<String> getDescription() {
        return description;
    }

    @Override
    public Optional<ContextualComponent> getReturnModel() {
        return returned;
    }

    @Override
    public Collection<? extends ContextualComponent> getParameterModels() {
        return params;
    }

    @Override
    public Collection<? extends ComponentAttribute> getAttributes() {
        return (Collection<ComponentAttribute>) attributes;
    }

    @Override
    public String toString() {
        return path.asString();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.path);
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
        final ImmutableOperationModel other = (ImmutableOperationModel) obj;
        if (!Objects.equals(this.path, other.path)) {
            return false;
        }
        return true;
    }

}
