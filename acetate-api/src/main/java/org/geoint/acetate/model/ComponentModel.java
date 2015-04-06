package org.geoint.acetate.model;

import java.util.Collection;
import org.geoint.acetate.model.annotation.Model;

/**
 * Component of a data model.
 */
public interface ComponentModel {

    /**
     * The {@link Model} annotations detected for this component.
     *
     * @return annotations identified as model relevant
     */
    Collection<? extends Model> getModelAnnotations();

}
