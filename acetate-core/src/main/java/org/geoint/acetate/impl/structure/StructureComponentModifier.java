package org.geoint.acetate.impl.structure;

/**
 * Modifies a data component.
 *
 *
 * @param <T> component value type
 */
@FunctionalInterface
public interface StructureComponentModifier<T> {

    StructureComponent<T> apply(StructureComponent<T> component);
}
