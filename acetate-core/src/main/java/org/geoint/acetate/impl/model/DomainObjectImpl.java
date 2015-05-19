package org.geoint.acetate.impl.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.geoint.acetate.data.transform.BinaryCodec;
import org.geoint.acetate.data.transform.CharacterCodec;
import org.geoint.acetate.impl.model.ImmutableContextPath.ImmutableObjectPath;
import org.geoint.acetate.model.ComposableComponent;
import org.geoint.acetate.model.DomainAggregateObject;
import org.geoint.acetate.model.DomainCompositeObject;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.ModelContextPath;
import org.geoint.acetate.model.DomainObject;
import org.geoint.acetate.model.DomainOperation;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.attribute.Inherited;
import org.geoint.acetate.model.builder.ComponentCollisionException;
import org.geoint.acetate.model.builder.DomainOperationBuilder;
import org.geoint.acetate.model.builder.IncompleteModelException;
import org.geoint.acetate.model.constraint.ComponentConstraint;

/**
 * Domain model object instance.
 *
 * @param <T>
 */
public abstract class DomainObjectImpl<T> implements DomainObject<T> {

    private final DomainModel model;
    private final ImmutableObjectPath path;
    private final String domainObjectName;
    private final Set<String> parentComponents;
    private final Optional<String> description;
    private final Collection<DomainOperation<?>> operations;
    private final Collection<DomainCompositeObject<?>> composites;
    private final Collection<DomainAggregateObject<?>> aggregates;
    private final Collection<ComponentConstraint> constraints;
    private final Collection<ComponentAttribute> attributes;
    private final BinaryCodec<T> binaryCodec;
    private final CharacterCodec<T> charCodec;

    private final static Logger logger = Logger.getLogger("org.geoint.acetate.model");

    protected DomainObjectImpl(DomainModel model,
            ImmutableObjectPath path,
            String name,
            String description,
            Collection<String> parentObjects,
            Collection<DomainOperation<?>> operations,
            Collection<DomainCompositeObject<?>> composites,
            Collection<DomainAggregateObject<?>> aggregates,
            Collection<ComponentConstraint> constraints,
            boolean inheritConstraints,
            Collection<ComponentAttribute> attributes,
            boolean inheritAttributes,
            BinaryCodec<T> binaryCodec,
            CharacterCodec<T> charCodec) throws IncompleteModelException, ComponentCollisionException {
        this.model = model;
        this.path = path;
        this.domainObjectName = name;
        this.parentComponents = Collections.unmodifiableSet(new HashSet<>(parentObjects));
        this.description = Optional.ofNullable(description);

        //inherit components and inheritable traits from each parent domain 
        //object.  parent domain objects do this themselves, exposing the results
        //through their interface, handling the recursion requirement
        final Set<String> localNames = new HashSet<>(); //ensure no collisions across component types
        for (String pn : parentComponents) {
            logger.log(Level.FINEST, "DomainObject ''{0}'' inheriting from "
                    + "parent ''{1}''", new Object[]{path.asString(), pn});

            Optional<DomainObject<?>> optionalParentObject
                    = model.getComponents().findByName(pn);
            if (!optionalParentObject.isPresent()) {
                throw new IncompleteModelException(model, "Unable to retrieve "
                        + "parent object model '" + pn + "' from the domain "
                        + "registry.");
            }
            final DomainObject parent = optionalParentObject.get();

            //for each parent, inherit any operations, composites, aggregates
            //and possibly constraints and attributes
            Map<String, DomainOperation<?>> combinedOps
                    = inherit(path, operations, parent, DomainObject::getOperations,
                            (basePath, inherited) -> {
                                return new DomainOperationBuilder(
                                        basePath.operation(inherited.getLocalName()),
                                        inherited)
                                .attribute(new Inherited(inherited.getComposite().getObjectName()))
                                .build(model);
                            });
        }
//
//        this.composites = inherit(composites, DomainObject::getComposites, collisionFilter);
//        this.aggregates = inherit(aggregates, DomainObject::getAggregates, collisionFilter);
//        //check if there were any composable local name collisions
//
//        //inhert data attributes and constraints
//        this.constraints = inherit(constraints, DomainObject::getConstraints);
//        this.attributes = inherit(attributes, DomainObject::getAttributes);

        this.binaryCodec = binaryCodec;
        this.charCodec = charCodec;
    }

    private <C extends ComposableComponent> Map<String, C> inherit(ImmutableObjectPath basePath,
            Collection<C> localComponents, DomainObject parent,
            //            Collection<C> parentComponents,
            Function<DomainObject, Collection<C>> getParentComponents,
            BiFunction<ImmutableObjectPath, C, C> createInherited)
            throws ComponentCollisionException {

        final Map<String, C> combinedComponents = new HashMap<>();

        //add local components
        for (final C lc : localComponents) {
            final String ln = lc.getLocalName();
            if (combinedComponents.containsKey(ln)) {
                throw new ComponentCollisionException(basePath, "Object defines"
                        + "two components with the same local name.");
            }
            combinedComponents.put(ln, lc);
        }

        //add any components which should be inherited
        for (C pc : getParentComponents.apply(parent)) {

            if (!pc.inherit()) {
                continue;
            }

            final String pln = pc.getLocalName();
            if (combinedComponents.containsKey(pln)) {
                logger.log(Level.FINE, () -> "Parent '"
                        + pc.getClass().getName() + "' component '"
                        + pln + "' not added, local component overrides.");
                continue;
            }
            C inheritedComponent = createInherited.apply(basePath, pc);
            logger.log(Level.FINE, () -> "Domain Object '" + basePath.asString()
                    + "' has inherited component '"
                    + inheritedComponent.getLocalName());
            combinedComponents.put(pln, inheritedComponent);
        }

        return combinedComponents;
    }

    @Override
    public ModelContextPath getPath() {
        return path;
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
    public Collection<DomainOperation<?>> getOperations() {
        return operations;
    }

    @Override
    public Collection<DomainAggregateObject<?>> getAggregates() {
        return aggregates;
    }

    @Override
    public Collection<DomainCompositeObject<?>> getComposites() {
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
    public Collection<DomainOperation<?>> getNativeOperations() {
        return operations.stream()
                .filter((o) -> o instanceof InheritedDomainOperation)
                .collect(Collectors.toList());
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

//    @Override
//    public DomainObject<? super T> inheritsFrom() {
//        return parentComponents.stream()
//                .map((pn) -> model.getComponents().findByName(pn))
//                .filter((optional) -> optional.isPresent())
//                .map((optional) -> (DomainObject<? super T>) optional.get())
//                .collect(Collectors.toSet());
//    }
    @Override
    public String toString() {
        return path.asString();
    }

}
