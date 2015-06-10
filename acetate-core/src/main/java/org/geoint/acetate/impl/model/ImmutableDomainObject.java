package org.geoint.acetate.impl.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geoint.acetate.data.transform.BinaryCodec;
import org.geoint.acetate.data.transform.CharacterCodec;
import org.geoint.acetate.model.CompositeComponent;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.address.ComponentAddress;
import org.geoint.acetate.model.DomainObject;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.builder.ComponentCollisionException;
import org.geoint.acetate.model.builder.IncompleteModelException;
import org.geoint.acetate.model.constraint.ComponentConstraint;
import org.geoint.acetate.model.util.ComponentFilters;

/**
 * Base domain model object.
 *
 * Implementations of this object must be immutable and thread-safe.
 *
 * @param <T> data type of the object
 */
public abstract class ImmutableDomainObject<T> implements DomainObject<T> {

    private final DomainModel model;
    private final ImmutableComponentAddress address;
    private final String domainObjectName;
    private final Set<DomainObject<? super T>> parents;
    private final Optional<String> description;
    private final Collection<ImmutableOperation<?>> operations;
    private final Collection<ImmutableCompositeObject<?>> composites;
    private final Collection<ImmutableAggregate<?>> aggregates;
    private final Collection<ComponentConstraint> constraints;
    private final Collection<ComponentAttribute> attributes;
    private final BinaryCodec<T> binaryCodec;
    private final CharacterCodec<T> charCodec;

    private final static Logger logger
            = Logger.getLogger("org.geoint.acetate.model");

    /**
     * Create a domain object with the absolute state of the domain object.
     *
     * This constructor creates shallow defensive copies of each collection
     * provided.
     * <p>
     * This constructor <b>does not</b> perform any composite/inheritance
     * roll-up. Use builders for this.
     *
     * @param model domain model this object belongs to
     * @param address domain model component contextual address for the object
     * @param name required domain-unique object display name
     * @param description optional object description (may be null)
     * @param parentObjectNames explictly defined object inheritance
     * @param components all object components
     * @param constraints constraints placed on this object
     * @param attributes attributes defined for this object
     * @param binaryCodec
     * @param charCodec
     * @throws IncompleteModelException
     * @throws ComponentCollisionException
     */
    protected ImmutableDomainObject(DomainModel model,
            ImmutableComponentAddress address,
            String name,
            String description,
            Collection<String> parentObjectNames,
            Collection<CompositeComponent> components,
            Collection<ComponentConstraint> constraints,
            Collection<ComponentAttribute> attributes,
            BinaryCodec<T> binaryCodec,
            CharacterCodec<T> charCodec)
            throws IncompleteModelException, ComponentCollisionException {
        this.model = model;
        this.address = address;
        this.domainObjectName = name;
        this.description = Optional.ofNullable(description);

        Set<DomainObject<? super T>> parentObjects = new HashSet<>();
        Collection<ImmutableOperation<?>> ops = new HashSet<>();
        Collection<ImmutableCompositeObject<?>> comps = new HashSet<>();
        Collection<ImmutableAggregate<?>> aggs = new HashSet<>();

        //add explictly defined parents
        parentObjectNames.stream()
                .map((pn) -> model.getComponents().findByName(pn))
                .filter((o) -> o.isPresent())
                .map((p) -> (DomainObject<? super T>) p.get())
                .forEach((p) -> {
                    logger.log(Level.FINE, () -> "Domain Object '"
                            + address.asString()
                            + "' inherits from '"
                            + p.getObjectName());
                    parentObjects.add(p);
                });

        for (CompositeComponent c : components) {

            if (ComponentFilters.isInherited(c)) {
                //add implict inheritence

                if (parentObjects.add(c.getDeclaringComponent())) {
                    logger.log(Level.FINE, () -> "Object '"
                            + address.asString() + "' inherits from '"
                            + c.getDeclaringComponent().getAddress().asString());
                }
            }

            if (c instanceof ImmutableOperation) {
                ops.add((ImmutableOperation<?>) c);
            } else if (c instanceof ImmutableAggregate) {
                aggs.add((ImmutableAggregate<?>) c);
            } else if (c instanceof ImmutableCompositeObject) {
                comps.add((ImmutableCompositeObject<?>) c);
            } else {
                throw new IncompleteModelException(model, "Unknown model component "
                        + c.getClass().getName());
            }
        }

        this.parents = Collections.unmodifiableSet(parentObjects);
        this.operations = Collections.unmodifiableCollection(ops);
        this.composites = Collections.unmodifiableCollection(comps);
        this.aggregates = Collections.unmodifiableCollection(aggs);
        this.constraints = Collections.unmodifiableCollection(new ArrayList<>(constraints));
        this.attributes = Collections.unmodifiableCollection(new ArrayList<>(attributes));
        this.binaryCodec = binaryCodec;
        this.charCodec = charCodec;
    }

    @Override
    public ComponentAddress getAddress() {
        return address;
    }

    @Override
    public String getObjectName() {
        return domainObjectName;
    }

    @Override
    public Optional<String> getDescription() {
        return description;
    }

    @Override
    public Collection<ImmutableOperation<?>> getOperations() {
        return operations;
    }

    @Override
    public Collection<ImmutableAggregate<?>> getAggregates() {
        return aggregates;
    }

    @Override
    public Collection<ImmutableCompositeObject<?>> getComposites() {
        return composites;
    }

    @Override
    public Collection<ComponentAttribute> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<ComponentConstraint> getConstraints() {
        return constraints;
    }

    @Override
    public CharacterCodec<T> getCharacterCodec() {
        return charCodec;
    }

    @Override
    public BinaryCodec<T> getBinaryCodec() {
        return binaryCodec;
    }

    @Override
    public DomainModel getDomainModel() {
        return model;
    }

    @Override
    public String toString() {
        return address.asString();
    }

}
