package org.geoint.acetate.model.builder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.geoint.acetate.impl.model.ImmutableContextPath.ImmutableObjectPath;
import org.geoint.acetate.impl.model.ImmutableContextPath.ImmutableOperationPath;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.DomainOperation;
import org.geoint.acetate.model.attribute.Inherited;

/**
 * Builds a domain object operation model.
 *
 * @param <R>
 */
public class DomainOperationBuilder<R>
        extends AbstractContextualComponentBuilder<R, DomainOperationBuilder> {

    private final Map<String, ContextualObjectBuilder<?>> params
            = new HashMap<>();
    private ContextualEventBuilder<R, ?> returned;

    public DomainOperationBuilder(ImmutableOperationPath path) {
        super(path);
    }

    /**
     * Creates an inherited instance of the domain operation.
     *
     * @param <R>
     * @param compositePath
     * @param inherited
     * @return inherited instance
     */
    public static <R> DomainOperation<R> inherit(
            ImmutableObjectPath compositePath,
            DomainOperation<R> inherited) {
        DomainOperationBuilder<?> ob = new DomainOperationBuilder(
                compositePath.operation(inherited.getLocalName()));
        return ob.attribute(new Inherited(inherited.getComposite().getObjectName()))
                .build(inherited.getDomainModel());
    }

    @Override
    public DomainOperation<R> build(DomainModel model) {

    }

    public ContextualEventBuilder<R, ?> returns(String objectName) {
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
    public Set<String> getDependencies() {
        Set<String> deps = new HashSet<>();
        this.params.values().stream()
                .forEach((b) -> {
                    deps.add(b.baseComponentName);
                    deps.addAll(b.getDependencies());
                });
        deps.add(this.returned.eventDomainObjectName);
        deps.addAll(this.returned.getDependencies());
        return deps;
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
