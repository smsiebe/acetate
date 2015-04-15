package org.geoint.acetate.bind.template;

import org.geoint.acetate.model.DataModel;

/**
 * A specific data structure format for a Java object.
 *
 * @param <T> java object for this template model
 */
public interface DataTemplate<T> {

    /**
     * The data model as derived from the template.
     *
     * @return derived DataModel from the template
     */
    DataModel<T> getModel();

    /**
     * Visit the data template parts.
     *
     * @param visitor
     */
    void visit(TemplateVisitor visitor);
}
