package org.geoint.acetate.impl.model;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import org.geoint.acetate.model.ModelContextPath;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.DomainObject;
import org.geoint.acetate.model.DomainOperation;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.event.DomainEntityEvent;

/**
 *
 * @param <R> operation retrun type
 */
public class DomainOperationImpl<R> implements DomainOperation<R> {

    protected final DomainModel model;
    protected final ModelContextPath path;
    protected final String operationName;
    protected final Optional<String> description;
    protected final DomainEntityEvent<R, ?> returned;
    protected final Collection<DomainObject<?>> params;
    protected final Collection<? extends ComponentAttribute> attributes;

    DomainOperationImpl(DomainModel model, ModelContextPath path,
            String name,
            Optional<String> description,
            DomainEntityEvent<R, ?> returned,
            Collection<DomainObject<?>> params,
            Collection<? extends ComponentAttribute> attributes) {
        this.model = model;
        this.path = path;
        this.operationName = name;
        this.description = description;
        this.returned = returned;
        this.params = params;
        this.attributes = attributes;
    }

    @Override
    public DomainModel getDomainModel() {
        return model;
    }

    @Override
    public ModelContextPath getPath() {
        return path;
    }

    @Override
    public String getName() {
        return operationName;
    }

    @Override
    public Optional<String> getDescription() {
        return description;
    }

    @Override
    public Collection<ComponentAttribute> getAttributes() {
        return (Collection<ComponentAttribute>) attributes;
    }

    @Override
    public DomainEntityEvent<R, ?> getReturnModel() {
        return returned;
    }

    @Override
    public Collection<DomainObject<?>> getParameterModels() {
        return params;
    }

    @Override
    public DomainObject<?> getComposite() {

    }

    @Override
    public String getLocalName() {

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
        final DomainOperationImpl other = (DomainOperationImpl) obj;
        if (!Objects.equals(this.path, other.path)) {
            return false;
        }
        return true;
    }

}
