package org.geoint.acetate.model.builder;

import java.util.HashMap;
import java.util.Map;
import org.geoint.acetate.impl.model.ImmutableContextPath.ImmutableObjectPath;
import org.geoint.acetate.impl.model.ImmutableContextPath.ImmutableOperationPath;
import org.geoint.acetate.impl.model.DomainOperationImpl;
import org.geoint.acetate.model.DomainModel;

/**
 * Builds a domain object operation model.
 *
 * @param <R>
 */
public class DomainOperationBuilder<R>
        extends AbstractComponentBuilder<R, DomainOperationBuilder<R>> {

    private final Map<String, ContextualObjectBuilder<?>> params
            = new HashMap<>();
    private ContextualEventBuilder<R> returned;

    public DomainOperationBuilder(ImmutableOperationPath path) {
        super(path);
    }

    @Override
    public DomainOperationImpl<R> build(DomainModel model) {

    }

    public ContextualEventBuilder<R> returns(String objectName) {
        if (returned != null) {
            return returned;
        }

        ImmutableObjectPath rp = path().returned();
        returned = new ContextualEventBuilder(rp, objectName);
        return returned;
    }

    public ContextualObjectBuilder<?> parameter(String paramName,
            String objectName, boolean isCollection) {
        if (params.containsKey(paramName)) {
            return params.get(paramName);
        }

        final ImmutableObjectPath pp = path().parameter(paramName);
        ContextualObjectBuilder pb
                = new ContextualObjectBuilder(pp, objectName, isCollection);
        params.put(paramName, pb);
        return pb;
    }

    @Override
    protected ImmutableOperationPath path() {
        return (ImmutableOperationPath) super.path();
    }

    @Override
    protected DomainOperationBuilder<R> self() {
        return this;
    }

}
