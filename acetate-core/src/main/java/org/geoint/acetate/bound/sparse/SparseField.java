package org.geoint.acetate.bound.sparse;

/**
 * Marker interface for a sparse field.
 *
 * @param <T> sparse data format
 */
public interface SparseField<T> {

    /**
     * Sparse field path.
     *
     * @return field path
     */
    String getPath();

    /**
     * Sparse data value.
     *
     * @return sparse data value
     */
    T getValue();

}
