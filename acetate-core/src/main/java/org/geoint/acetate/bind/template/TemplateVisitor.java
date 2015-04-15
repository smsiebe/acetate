package org.geoint.acetate.bind.template;

/**
 * Passed to a {@link DataTemplate} to sequentially visit each
 * {@link TemplatePart} from which it is comprised.
 *
 */
@FunctionalInterface
public interface TemplateVisitor {

    /**
     * Called for each part of the template, iterated in the sequence of the
     * template.
     *
     * @param part template part
     */
    void visit(TemplatePart part);
}
