package org.geoint.acetate.impl.structure;

import gov.ic.geoint.acetate.bind.DataType;

/**
 * Uses an alternative data type.
 *
 * @param <T> data type
 */
public final class ComponentDataTypeModifier<T>
        implements StructureComponentModifier<T> {

    private final DataType<T> alternativeDataType;

    public ComponentDataTypeModifier(DataType<T> alternativeDataType) {
        this.alternativeDataType = alternativeDataType;
    }

    @Override
    public StructureComponent<T> apply(StructureComponent<T> component) {
        return new StructureComponent(component.getPosition(),
                alternativeDataType,
                component.getConstraints(), component.getAttributes(),
                component.getAliases(), component.getCodecChain());
    }

}
