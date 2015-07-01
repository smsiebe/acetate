package org.geoint.acetate.domain.model;

import java.util.Collection;
import org.geoint.acetate.domain.annotation.Model;

/**
 * A data model consisting of other data models.
 */
@Model(name = "ontology",
        domainName = DomainModel.ACETATE_DOMAIN_NAME,
        domainVersion = DomainModel.ACETATE_DOMAIN_VERSION)
public interface Ontology extends DomainModel {

    /**
     * The domain models that compose this ontology.
     *
     * @return domain models which compose this ontology
     */
    Collection<DomainModel> getDomains();
}
