package org.geoint.acetate.model;

import org.geoint.acetate.model.annotation.Model;

/**
 * Models a composite relationship between two data model components.
 */
@Model(name="", domainName="acetate", domainVersion=1)
public interface CompositeModel {

    /**
     * Context-unique composite name for this relationship.
     *
     * Often (though not required) the contextual composite name for the data
     * component will be preferred during data binding.
     *
     * @return context-unique name given to this composite relationship
     */
    String getCompositeName();

    /**
     * Contextually-defined data model.
     * 
     * @return context-defined data model of the model component 
     */
    ContextualDataModel getDataModel();
}
