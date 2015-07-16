package org.geoint.acetate.domain.model;

import java.util.Collection;
import org.geoint.acetate.domain.annotation.Accessor;
import org.geoint.acetate.domain.annotation.Entity;
import org.geoint.acetate.domain.annotation.MultiComposite;

/**
 * A data model consisting of other data models.
 */
@Entity(domain = DataModel.ACETATE_DOMAIN_NAME,
        name = "ontology",
        displayName = "Ontology")
public interface Ontology extends Taxonomy {

    /**
     * Imported taxonomies within this ontology.
     *
     * @return imported taxonomies used in this ontology
     */
    @Accessor(name="getImports")
    @MultiComposite(name = "imports", displayName = "Imported Taxonomies")
    Collection<Taxonomy> getImportedTaxonomies();
}
