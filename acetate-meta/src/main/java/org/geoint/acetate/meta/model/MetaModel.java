package org.geoint.acetate.meta.model;

import java.util.Collection;

/**
 * Defines a metamodel which make use of the object models.
 *
 * The metamodel, the model of a model, models an application-defined model.
 */
public interface MetaModel {

    /**
     * The metamodel name (for example, <i>domain</i> or <i>service</i>).
     *
     * @return the metamodel name
     */
    String getName();

    /**
     * Objects which are part of the meta model.
     *
     * @return model components of the meta model
     */
    Collection<ObjectModel> getModels();
}
