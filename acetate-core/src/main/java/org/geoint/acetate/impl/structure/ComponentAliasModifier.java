package org.geoint.acetate.impl.structure;

import java.util.HashSet;
import java.util.Set;

/**
 * Adds an alias to the component.
 */
public final class ComponentAliasModifier<T>
        implements StructureComponentModifier<T> {

    private final String alias;

    public ComponentAliasModifier(String alias) {
        this.alias = alias;
    }

    @Override
    public StructureComponent<T> apply(StructureComponent<T> component) {
        Set<String> aliases = component.getAliases();
        if (!aliases.contains(alias)) {
            aliases = new HashSet<>(aliases);
            aliases.add(alias);
        }
        return new StructureComponent(component.getPosition(),
                component.getType(),
                component.getConstraints(), component.getAttributes(),
                aliases, component.getCodecChain());
    }

}
