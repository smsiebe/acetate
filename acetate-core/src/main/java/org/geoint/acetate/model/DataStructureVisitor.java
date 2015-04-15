package org.geoint.acetate.model;

/**
 * Visitor used to hierarchically traverse a {@link DataStructure}.
 *
 */
@FunctionalInterface
public interface DataStructureVisitor {

    /**
     * Visit each component of the data structure in the same sequence in which
     * the data is rendered in a data structure.
     *
     * @param structure structural type
     * @param position dot-separated hierarchical position
     * @param model model
     */
    void visit(StructureType structure, String position, DataModel<?> model);
}
