package org.geoint.acetate.impl.structure;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.geoint.acetate.model.attribute.ComponentAttribute;
import org.geoint.acetate.model.ComponentModel;
import org.geoint.acetate.model.constraint.ComponentConstraint;
import gov.ic.geoint.acetate.bind.DataType;
import org.geoint.acetate.codec.BinaryCodec;

/**
 * Immutable context-specific data component.
 *
 * @param <T>
 */
public class StructureComponent<T> implements ComponentModel<T> {

    private final String position;
    private final DataType<T> type;
    private final Collection<ComponentConstraint<T>> constraints;
    private final Collection<ComponentAttribute> attributes;
    private final Set<String> aliases;
    private final List<BinaryCodec> codecChain;

    StructureComponent(String position, DataType<T> type,
            Collection<ComponentConstraint<T>> constraints,
            Collection<ComponentAttribute> attributes) {
        this(position, type, constraints, attributes,
                Collections.EMPTY_SET, Collections.EMPTY_LIST);
    }

    StructureComponent(String position, DataType<T> type,
            Collection<ComponentConstraint<T>> constraints,
            Collection<ComponentAttribute> attributes, Set<String> aliases,
            List<BinaryCodec> codecChain) {
        this.position = position;
        this.type = type;
        this.constraints = constraints;
        this.attributes = attributes;
        //defensive copy...but since we only use the structure component 
        //internally there is no need to make it unmodifiable
        this.aliases = new HashSet<>(aliases);
        this.codecChain = new LinkedList<>(codecChain);
    }

    /**
     * Create a StructureComponent from the models data component plus any
     * modifiers.
     *
     * @param position absolute structural position
     * @param component
     * @param modifiers
     * @return context-specific structure component
     */
    public static <T> StructureComponent<T> fromModel(String position,
            ComponentModel<T> component,
            StructureComponentModifier... modifiers) {

        StructureComponent sc = new StructureComponent(position,
                component.getType(),
                component.getConstraints(),
                component.getAttributes());

        for (StructureComponentModifier m : modifiers) {
            sc = m.apply(sc);
        }
        return sc;
    }

    public String getPosition() {
        return position;
    }

    public Set<String> getAliases() {
        return aliases;
    }

    public List<BinaryCodec> getCodecChain() {
        return codecChain;
    }

    @Override
    public DataType<T> getType() {
        return type;
    }

    @Override
    public Collection<ComponentConstraint<T>> getConstraints() {
        return constraints;
    }

    @Override
    public Collection<ComponentAttribute> getAttributes() {
        return attributes;
    }

}
