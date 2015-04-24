package org.geoint.acetate.impl.structure;

import java.util.Arrays;
import java.util.LinkedList;
import org.geoint.acetate.transform.BinaryCodec;

/**
 * Completely replaces the component codec chain for a component.
 *
 * @param <T>
 */
public final class ComponentCodecChainModifier<T>
        implements StructureComponentModifier<T> {

    private final LinkedList<BinaryCodec> codecChain;

    public ComponentCodecChainModifier(BinaryCodec... codecChain) {
        this.codecChain = new LinkedList<>();
        Arrays.stream(codecChain).forEach((c) -> this.codecChain.add(c));

    }

    @Override
    public StructureComponent<T> apply(StructureComponent<T> component) {
        return new StructureComponent(component.getPosition(),
                component.getType(),
                component.getConstraints(), component.getAttributes(),
                component.getAliases(), codecChain);
    }

}
