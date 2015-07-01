package org.geoint.acetate.entity.atticl.util;

import java.util.function.Predicate;
import org.geoint.acetate.entity.attic.attribute.Attributed;
import org.geoint.acetate.entity.attic.attribute.Inherited;

/**
 * Utility class providing common component filter predicates as functional
 * methods.
 */
public final class ComponentFilters {

    /**
     * Determines if the {@link Attributed} component is locally defined based
     * on the component attributes.
     *
     * This method is designed to be used as a method reference by implementing
     * the {@link Predicate} signature.
     *
     * @param component component to check
     * @return true if the component was declared locally (not inherited or from
     * composite)
     */
    public static boolean isLocal(Attributed component) {
        return !component.hasAttribute(Inherited.class);
    }

    /**
     * Determines if the domain component is inherited from a parent object.
     *
     * This method is designed to be used as a method reference by implementing
     * the {@link Predicate} signature.
     *
     * @param component component to check
     * @return true if the component is inherited from a parent object model
     */
    public static boolean isInherited(Attributed component) {
        return component.hasAttribute(Inherited.class);
    }
}
