package org.geoint.acetate.impl.model;

import java.util.ArrayList;
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
import org.geoint.acetate.data.transform.BinaryCodec;
import org.geoint.acetate.data.transform.CharacterCodec;
import org.geoint.acetate.impl.model.ImmutableContextPath.ImmutableObjectPath;
import org.geoint.acetate.model.ComposableComponent;
import org.geoint.acetate.model.DomainAggregateObject;
import org.geoint.acetate.model.DomainCompositeObject;
import org.geoint.acetate.model.DomainModel;
import org.geoint.acetate.model.ComponentContextPath;
import org.geoint.acetate.model.DomainObject;
import org.geoint.acetate.model.DomainOperation;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.builder.ComponentCollisionException;
import org.geoint.acetate.model.builder.IncompleteModelException;
import org.geoint.acetate.model.constraint.ComponentConstraint;

/**
 * Base domain model object.
 *
 * Implementations of this object must be immutable and thread-safe.
 *
 * @param <T> data type of the object
 */
public abstract class ImmutableDomainObject<T> implements DomainObject<T> {

    private final DomainModel model;
    private final ImmutableObjectPath path;
    private final String domainObjectName;
    private final Set<String> parentObjectNames;
    private final Optional<String> description;
    private final Collection<DomainOperation<?>> operations;
    private final Collection<DomainCompositeObject<?>> composites;
    private final Collection<DomainAggregateObject<?>> aggregates;
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
     * @param model
     * @param path
     * @param name
     * @param description
     * @param parentObjectNames
     * @param operations including native, inherited, composite
     * @param composites including native, inherited, composite
     * @param aggregates including native, inherited, composite
     * @param constraints
     * @param attributes
     * @param binaryCodec
     * @param charCodec
     * @throws IncompleteModelException
     * @throws ComponentCollisionException
     */
    protected ImmutableDomainObject(DomainModel model,
            ImmutableObjectPath path,
            String name,
            String description,
            Collection<String> parentObjectNames,
            Collection<ImmutableDomainOperation<?>> operations, //includes inherited, composite
            Collection<ImmutableDomainComposite<?>> composites, //includes inherited, composite
            Collection<ImmutableDomainAggregate<?>> aggregates, //includes inherited, composite
            Collection<ComponentConstraint> constraints,
            Collection<ComponentAttribute> attributes,
            BinaryCodec<T> binaryCodec,
            CharacterCodec<T> charCodec)
            throws IncompleteModelException, ComponentCollisionException {
        this.model = model;
        this.path = path;
        this.domainObjectName = name;
        this.description = Optional.ofNullable(description);
        this.parentObjectNames
                = Collections.unmodifiableSet(new HashSet<>(parentObjectNames));
        this.operations
                = Collections.unmodifiableCollection(new ArrayList<>(operations));
        this.composites
                = Collections.unmodifiableCollection(new ArrayList<>(composites));
        this.aggregates
                = Collections.unmodifiableCollection(new ArrayList<>(aggregates));
        this.constraints
                = Collections.unmodifiableCollection(new ArrayList<>(constraints));
        this.attributes
                = Collections.unmodifiableCollection(new ArrayList<>(attributes));
        this.binaryCodec = binaryCodec;
        this.charCodec = charCodec;
    }

    protected void inherit() {
//         //inherit components and inheritable traits from each parent domain 
//        //object.  parent domain objects do this themselves, exposing the results
//        //through their interface, handling the recursion requirement
//        final Set<String> localNames = new HashSet<>(); //ensure no collisions across component types
//        for (String pn : parentComponents) {
//            logger.log(Level.FINEST, "DomainObject ''{0}'' inheriting from "
//                    + "parent ''{1}''", new Object[]{path.asString(), pn});
//
//            Optional<DomainObject<?>> optionalParentObject
//                    = model.getComponents().findByName(pn);
//            if (!optionalParentObject.isPresent()) {
//                throw new IncompleteModelException(model, "Unable to retrieve "
//                        + "parent object model '" + pn + "' from the domain "
//                        + "registry.");
//            }
//            final DomainObject parent = optionalParentObject.get();
//
//            //for each parent, inherit any operations, composites, aggregates
//            //and possibly constraints and attributes
//            Map<String, DomainOperation<?>> combinedOps
//                    = inherit(path, operations, parent, DomainObject::getOperations,
//                            (basePath, inherited) -> {
//                                return new DomainOperationBuilder(
//                                        basePath.operation(inherited.getLocalName()),
//                                        inherited)
//                                .attribute(new Inherited(inherited.getComposite().getObjectName()))
//                                .build(model);
//                            });
//        }
////
////        this.composites = inherit(composites, DomainObject::getComposites, collisionFilter);
////        this.aggregates = inherit(aggregates, DomainObject::getAggregates, collisionFilter);
////        //check if there were any composable local name collisions
////
////        //inhert data attributes and constraints
////        this.constraints = inherit(constraints, DomainObject::getConstraints);
////        this.attributes = inherit(attributes, DomainObject::getAttributes);

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
    public ComponentContextPath getPath() {
        return path;
    }

    @Override
    public Collection<String> getParentObjectNames() {
        return parentObjectNames;
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
        return path.asString();
    }

}
