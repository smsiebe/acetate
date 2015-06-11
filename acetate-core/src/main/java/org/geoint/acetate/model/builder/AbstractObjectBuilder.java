package org.geoint.acetate.model.builder;

import java.util.HashMap;
import java.util.Map;
import org.geoint.acetate.data.transform.BinaryCodec;
import org.geoint.acetate.data.transform.CharacterCodec;
import org.geoint.acetate.impl.model.ImmutableObjectAddress;
import org.geoint.acetate.impl.model.ImmutableObjectAddress.ImmutableComponentAddress;

/**
 * Abstract domain model object builder.
 *
 * @param <T> type of the domain model object
 * @param <B> concrete builder type, so builder API generics are happy
 */
public abstract class AbstractObjectBuilder<T, B extends AbstractObjectBuilder<T, B>>
        extends AbstractComponentBuilder<T, B> {

    //key = localname
    protected final Map<String, DomainOperationBuilder<?>> operations
            = new HashMap<>();
    //key = localname
    protected final Map<String, DomainCompositeObjectBuilder<?>> composites
            = new HashMap<>();
    //key = localname
    protected final Map<String, DomainAggregateObjectBuilder<?>> aggregates
            = new HashMap<>();
    protected BinaryCodec<T> binaryCodec;
    protected CharacterCodec<T> charCodec;

    public AbstractObjectBuilder(ImmutableObjectAddress path) {
        super(path);
    }

    /**
     * Defines an object operation.
     *
     * @param localName component operation local name
     * @return domain model operation builder
     * @throws ComponentCollisionException thrown if this local component name
     * is being used by a composite or aggregate
     */
    public DomainOperationBuilder<?> operation(String localName)
            throws ComponentCollisionException {
        final ImmutableObjectAddress.ImmutableOperationPath op = path().operation(localName);
        return getOrCreate(op, () -> new DomainOperationBuilder(op),
                operations, composites, aggregates);
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
        final ImmutableObjectAddress cp = path().composite(localName);
        return getOrCreate(cp,
                () -> new DomainCompositeObjectBuilder(cp, objectName, isCollection),
                composites, operations, aggregates);
    }

    /**
     * Adds an aggregate object to this component.
     *
     * The builder returned from this method modifies the object model when in
     * context as this object aggregate, not the base object model definition.
     *
     * @param localName
     * @param objectName
     * @param isCollection
     * @return aggregate context builder
     * @throws ComponentCollisionException thrown if this local component name
     * is being used by a composite or aggregate
     */
    public DomainAggregateObjectBuilder<?> aggregate(String localName,
            String objectName, boolean isCollection)
            throws ComponentCollisionException {
        final ImmutableObjectAddress ap = path().aggregate(localName);
        return getOrCreate(ap,
                () -> new DomainAggregateObjectBuilder(ap, objectName, isCollection),
                aggregates, composites, operations);
    }

    /**
     * Explicitly sets the codec to use to convert to/from an object for the
     * base domain model object.
     *
     * @param codec
     * @return this builder (fluid interface)
     */
    public B codec(BinaryCodec<T> codec) {
        this.binaryCodec = codec;
        return self();
    }

    /**
     * Explicitly sets the character codec for the base domain model object.
     *
     * @param codec
     * @return this builder (fluid interface)
     */
    public B codec(CharacterCodec<T> codec) {
        this.charCodec = codec;
        return self();
    }

    @Override
    protected ImmutableObjectAddress path() {
        return (ImmutableObjectAddress) path;
    }

    /*
     * Return the component builder if it already exists, else throw exception
     * if a different component type is using that name, else create new 
     * component, register and return.
     */
    private <B> B getOrCreate(ImmutableObjectAddress path,
            BuilderFactory<B> factory,
            Map<String, B> checkMap,
            Map<String, ?>... otherComponents)
            throws ComponentCollisionException {

        final String localName = path.getComponentName();

        if (checkMap.containsKey(localName)) {
            //return existing component builder
            return checkMap.get(localName);
        }

        //check if the component name is used by other component types
        for (Map<String, ?> o : otherComponents) {
            if (o.containsKey(localName)
                    || o.containsKey(localName)
                    || o.containsKey(localName)) {
                throw new ComponentCollisionException(path);
            }
        }

        //create the new builder, register it, and return it
        final B ob = factory.create();
        checkMap.put(localName, ob);
        return ob;
    }

    @FunctionalInterface
    private interface BuilderFactory<B> {

        B create();
    }
}
