package org.geoint.acetate.impl.structure;

import java.util.Collection;
import java.util.stream.Collectors;
import org.geoint.acetate.model.constraint.ComponentConstraint;

/**
 *
 * @param <T>
 */
public final class ComponentConstraintModifier<T>
        implements StructureComponentModifier<T> {

    private final ComponentConstraint<T> constraint;

    public ComponentConstraintModifier(ComponentConstraint<T> constraint) {
        this.constraint = constraint;
    }

    @Override
    public StructureComponent<T> apply(StructureComponent<T> component) {
        Collection<ComponentConstraint<T>> newConstraints
                = component.getConstraints().parallelStream()
                .filter((c) -> c.getClass().equals(constraint.getClass()))
                .collect(Collectors.toList());
        newConstraints.add(constraint);
        return new StructureComponent(component.getPosition(),
                component.getType(),
                newConstraints, component.getAttributes(),
                component.getAliases(), component.getCodecChain());
    }

}
