package org.geoint.acetate.model;

import java.util.Collection;
import org.geoint.acetate.model.annotation.Domain;

/**
 * A data model consisting of other data models.
 */
@Domain(name = "acetate", version = 1)
public interface Ontology extends DataModel {

    Collection<DomainModel> getDomains();
}
