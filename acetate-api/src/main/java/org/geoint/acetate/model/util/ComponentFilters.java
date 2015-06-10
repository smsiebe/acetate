package org.geoint.acetate.model.util;

import java.util.function.Predicate;
import org.geoint.acetate.model.attribute.Attributable;
import org.geoint.acetate.model.attribute.Composited;
import org.geoint.acetate.model.attribute.Inherited;

/**
 * Utility class providing common component filter predicate functional methods.
 */
public final class ComponentFilters {

    /**
     * Determines if the {@link Attributable} component is locally defined based
     * on the component attributes.
     *
     * This method is designed to be used as a method reference by implementing
     * the {@link Predicate} signature.
     *
     * @param component component to check
     * @return true if the component was declared locally (not inherited or from
     * composite)
     */
    public static boolean isLocal(Attributable component) {
        return component.getAttributes().stream()
                .map((a) -> a.getClass())
                .anyMatch((ac) -> ac.equals(Inherited.class)
                        || ac.equals(Composited.class));
    }

    /**
     * Determines if the {@link Attributable} component is defined by a
     * composite component of its object model.
     *
     * This method is designed to be used as a method reference by implementing
     * the {@link Predicate} signature.
     *
     * @param component component to check
     * @return true if the component is defined on a composite component of the
     * object
     */
    public static boolean isComposite(Attributable component) {
        return component.hasAttribute(Composited.class);
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
    public static boolean isInherited(Attributable component) {
        return component.hasAttribute(Inherited.class);
    }
}
