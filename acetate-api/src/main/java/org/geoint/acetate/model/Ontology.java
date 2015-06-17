package org.geoint.acetate.model;

import java.util.Collection;
import org.geoint.acetate.model.annotation.Model;

/**
 * A data model consisting of other data models.
 */
@Model(name = "Ontology", domainName = "acetate", domainVersion = 1)
public interface Ontology extends DataModel {

    /**
     * The domain models that compose this ontology.
     *
     * @return domain models which compose this ontology
     */
    Collection<DomainModel> getDomains();
}
