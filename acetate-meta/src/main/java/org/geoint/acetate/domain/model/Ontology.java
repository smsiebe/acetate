package org.geoint.acetate.domain.model;

import java.util.Collection;
import org.geoint.acetate.domain.annotation.Object;

/**
 * A data model consisting of other data models.
 */
@Object(name = "ontology", domainName = "acetate", domainVersion = "1.0-BETA")
public interface Ontology extends DomainModel {

    /**
     * The domain models that compose this ontology.
     *
     * @return domain models which compose this ontology
     */
    Collection<DomainModel> getDomains();
}
