package org.geoint.acetate.data.common;

/**
 * Identifies a data type that has an (optional) lower limit and (optional)
 * upper limit.
 *
 * @param <T>
 */
public interface Ranged<T> {

    /**
     * The lower limit of the ranged data item.
     *
     * @return the lower limit of the range, or null if unknown
     */
    T getLow();

    /**
     * The upper limit of the ranged data item.
     *
     * @return the upper limit of the range, or null if unknown.
     */
    T getHigh();

    /**
     * Determine if the provided value is between the ranged values, inclusive.
     *
     * @param value value of check
     * @return true if the provided value is between the lower and upper range
     * value
     * @throws UnboundedRangeException if the lower or upper limit is not known
     */
    boolean isWithin(T value) throws UnboundedRangeException;

    /**
     * Determine if the provided value if below the lower limit of the ranged
     * item, exclusive.
     *
     * @param value value to check
     * @return true if the provided value is below the lower limit of the ranged
     * data item
     * @throws UnboundedRangeException if the lower range value is not known
     */
    boolean isBelow(T value) throws UnboundedRangeException;

    /**
     * Determine if the provided value is above the upper limit of the ranged
     * data item, exclusive.
     *
     * @param value value to check
     * @return true if the provided value is above the upper limit of the ranged
     * data item
     * @throws UnboundedRangeException if the upper range value is not known
     */
    boolean isAbove(T value) throws UnboundedRangeException;
}
