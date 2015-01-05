package org.geoint.acetate.lang;

/**
 * A range of double values.
 */
public final class DoubleRange implements Ranged<Double> {

    private final Double lowerLimit;
    private final Double upperLimit;

    public DoubleRange(Double lowerLimit, Double upperLimit) {
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    @Override
    public Double getLow() {
        return lowerLimit;
    }

    @Override
    public Double getHigh() {
        return upperLimit;
    }

    @Override
    public boolean isWithin(Double value) throws UnboundedRangeException {
        if (lowerLimit == null || upperLimit == null) {
            throw new UnboundedRangeException("Unable to determine if value is "
                    + "within range, bounds of the range are not specified.");
        }
        return value.compareTo(lowerLimit) >= 0
                && value.compareTo(upperLimit) <= 0;
    }

    @Override
    public boolean isBelow(Double value) throws UnboundedRangeException {
        if (lowerLimit == null) {
            throw new UnboundedRangeException("Unable to determine if value is "
                    + "below the lower limit, lower limit bound of the range"
                    + " is not specified.");
        }
        return value.compareTo(lowerLimit) < 0;
    }

    @Override
    public boolean isAbove(Double value) throws UnboundedRangeException {
        if (upperLimit == null) {
            throw new UnboundedRangeException("Unable to determine if value is "
                    + "above the upper limit, upper limit bound of the range"
                    + " is not specified.");
        }
        return value.compareTo(upperLimit) > 0;
    }

    @Override
    public String toString() {
        return lowerLimit + "-" + upperLimit;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + (this.lowerLimit != null ? this.lowerLimit.hashCode() : 0);
        hash = 71 * hash + (this.upperLimit != null ? this.upperLimit.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DoubleRange other = (DoubleRange) obj;
        if (this.lowerLimit != other.lowerLimit && (this.lowerLimit == null || !this.lowerLimit.equals(other.lowerLimit))) {
            return false;
        }
        if (this.upperLimit != other.upperLimit && (this.upperLimit == null || !this.upperLimit.equals(other.upperLimit))) {
            return false;
        }
        return true;
    }

}
