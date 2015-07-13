package org.geoint.acetate.model;

import java.util.Collection;

/**
 * A data model consisting of other data models.
 */
public interface Ontology extends Taxonomy {

    /**
     * Imported taxonomies within this ontology.
     *
     * @return imported taxonomies used in this ontology
     */
    Collection<Taxonomy> getImportedTaxonomies();
}
