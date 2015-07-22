package org.geoint.acetate.model;

import java.util.function.Predicate;

/**
 * Visits a ModelElement and related model elements, as applicable.
 *
 * @see ModelElement#visit(ModelVisitor)
 */
@FunctionalInterface
public interface ModelVisitor {

    static final VisitorOption[] NO_OPTIONS = {};
    static final Predicate[] NO_FILTERS = {};

    /**
     * Notified, at least once, for each model element.
     *
     * <h2>Duplicate ModelElement Notifications</h2>
     * By default, a ModelVisitor may be notified of a model element more than
     * once if. This may occur, for example, if the model contains many complex
     * or bidirectional relationships. Although the visitor will be notified of
     * this relationship by calling the 
     * {@link ModelVisitor#visit(ModelElement) } method more than once, a
     * ModelElements (transitive) relationships will only be notified once,
     * preventing an eternal loop.
     * <p>
     * If desired, the ModelVisitor can be configured to not receive duplicate
     * notifications for a ModelElement, only being notified once per
     * ModelElement instance. This can be done by including the
     * {@link ModelVisitor.VisitorOption#NOTIFY_ONCE_PER_MODEL} option in the
     * options array returned from {@link #options}.
     *
     * @param model
     */
    void visit(ModelElement model);

    /**
     * Returns the visitation options for the visitor instance.
     * <p>
     * By default no options are returned.
     *
     * @return model visitor options
     */
    default VisitorOption[] options() {
        return NO_OPTIONS;
    }

    /**
     * Returns filters used to limit the models notified to the visitor.
     * <p>
     * By default no filters are returned.
     *
     * @return
     */
    default Predicate<ModelElement>[] filters() {
        return NO_FILTERS;
    }

    public enum VisitorOption {

        /**
         * Only notify the visitor once per model.
         * <p>
         * Models that are encountered as a relationship in the model will not
         * be notified to the visitor. This option isn't necessary to prevent
         * eternal loops, however, just deduplicate the notification if desired.
         */
        NOTIFY_ONCE_PER_MODEL;
    }
}
