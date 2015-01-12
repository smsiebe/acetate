package org.geoint.acetate.data.temp;

import org.geoint.acetate.data.common.DoubleRange;
import org.geoint.acetate.data.common.Ranged;
import org.geoint.acetate.data.common.UnboundedRangeException;
import org.geoint.acetate.data.unit.TempUnit;

/**
 * A temperature range that internally manages the temperatures in celsius.
 */
public class TemperatureRange implements Ranged<Temperature> {

    private final Temperature low;
    private final Temperature high;

    public TemperatureRange(double lower, double upper, TempUnit unit) {
        this.low = Temperature.valueOf(lower, unit);
        this.high = Temperature.valueOf(upper, unit);
    }

    @Override
    public Temperature getLow() {
        return low;
    }

    public Double getLow(TempUnit unit) {
        return low.get(unit);
    }

    @Override
    public Temperature getHigh() {
        return high;
    }

    public Double getHigh(TempUnit unit) {
        return high.get(unit);
    }

    @Override
    public boolean isWithin(Temperature value) throws UnboundedRangeException {
        try {
            return value.isAbove(low) && value.isBelow(high);
        } catch (NullPointerException ex) {
            throw new UnboundedRangeException("Unable to determine if temperature"
                    + "is within range.");
        }
    }

    @Override
    public boolean isBelow(Temperature value) throws UnboundedRangeException {
        return value.isBelow(low);
    }

    @Override
    public boolean isAbove(Temperature value) throws UnboundedRangeException {
        return value.isAbove(high);
    }
}
