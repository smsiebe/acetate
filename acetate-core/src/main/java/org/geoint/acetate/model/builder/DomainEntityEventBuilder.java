package org.geoint.acetate.model.builder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.geoint.acetate.impl.model.ImmutableObjectAddress.ImmutableComponentAddress;
import org.geoint.acetate.model.ModelComponent;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.EventModel;

/**
 *
 * @param <T> domain entity event type
 * @param <E> object type of the entity object for this event
 */
public class DomainEntityEventBuilder<T, E>
        extends AbstractComponentBuilder<T, DomainEntityEventBuilder<T, E>> {

    private final String eventDomainObjectName;
    private String guidComponent;
    private String timeComponent;
    private String entityGuidComponent;
    private String entityVersionComponent;
    private final String entityDomainObjectName;
    private final Map<String, DomainCompositeObjectBuilder<?>> composites
            = new HashMap<>();

    public DomainEntityEventBuilder(ImmutableComponentAddress path,
            String eventDomainObjectName, String entityDomainObjectName) {
        super(path);
        this.eventDomainObjectName = eventDomainObjectName;
        this.entityDomainObjectName = entityDomainObjectName;
    }

    public EventModel<T, E> build() {

    }

    public DomainEntityEventBuilder<T, E> eventGuidComponent(
            final String componentName) {
        this.guidComponent = componentName;
        return this;
    }

    public DomainEntityEventBuilder<T, E> timeComponent(
            final String componentName) {
        this.timeComponent = componentName;
        return this;
    }

    public DomainEntityEventBuilder<T, E> entityGuidComponent(
            final String componentName) {
        this.entityGuidComponent = componentName;
        return this;
    }

    public DomainEntityEventBuilder<T, E> entityVersionComponent(
            final String componentName) {
        this.entityVersionComponent = componentName;
        return this;
    }

    /**
     * Add a composite object to this component.
     *
     * The builder returned from this method modifies the object model when in
     * context as this object composite, not the base object model definition.
     *
     * @param localName component-unique composite name
     * @param objectName domain-unique component name for this composite type
     * @param isCollection true if this is a collection
     * @return composite context builder
     * @throws ComponentCollisionException thrown if this local component name
     * is being used by a composite or aggregate
     */
    public DomainCompositeObjectBuilder<?> composite(String localName,
            String objectName, boolean isCollection)
            throws ComponentCollisionException {

        if (composites.containsKey(localName)) {
            return composites.get(localName);
        }

        final ImmutableComponentAddress cp = path().composite(localName);
        DomainCompositeObjectBuilder cb
                = new DomainCompositeObjectBuilder(cp, objectName, isCollection);
        composites.put(localName, cb);
        return cb;
    }

    @Override
    protected ImmutableComponentAddress path() {
        return (ImmutableComponentAddress) super.path();
    }

    @Override
    protected DomainEntityEventBuilder<T, E> self() {
        return this;
    }

    @Override
    public ModelComponent<T> build(DomainModel model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<String> getDependencies() {
    }

}
