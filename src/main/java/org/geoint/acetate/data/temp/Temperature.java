package org.geoint.acetate.data.temp;

import org.geoint.acetate.data.unit.TempUnit;

/**
 *
 */
public final class Temperature implements Comparable<Temperature> {

    private final Double celsius;

    private Temperature(Double celsius) {
        this.celsius = celsius;
    }

    public static Temperature celsius(Double c) {
        return new Temperature(c);
    }

    public static Temperature farenheight(Double f) {
        return new Temperature(TempUnit.C.convert(f, TempUnit.F));
    }

    public static Temperature kelvin(Double k) {
        return new Temperature(TempUnit.C.convert(k, TempUnit.K));
    }

    public static Temperature valueOf(Double t, TempUnit v) {
        return new Temperature(v.toCelsius(t));
    }

    public Double asCelsius() {
        return celsius;
    }

    public Double asFarenheight() {
        return (celsius != null)
                ? TempUnit.F.convert(celsius, TempUnit.C)
                : null;
    }

    public Double asKelvin() {
        return (celsius != null)
                ? TempUnit.K.convert(celsius, TempUnit.C)
                : null;
    }

    public Double get(TempUnit unit) {
        return (celsius != null)
                ? unit.convert(celsius, TempUnit.C)
                : null;
    }

    public boolean isBelow(Temperature temp) {
        if (temp == null || celsius == null) {
            throw new NullPointerException();
        }
        return celsius > temp.celsius;
    }

    public boolean isAbove(Temperature temp) {
        if (temp == null || celsius == null) {
            throw new NullPointerException();
        }
        return celsius < temp.celsius;
    }

    @Override
    public String toString() {
        return (celsius != null) ? celsius + "C" : "";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + (int) (Double.doubleToLongBits(this.celsius) ^ (Double.doubleToLongBits(this.celsius) >>> 32));
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
        final Temperature other = (Temperature) obj;
        if (Double.doubleToLongBits(this.celsius) != Double.doubleToLongBits(other.celsius)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Temperature o) {
        return celsius.compareTo(o.celsius);
    }

}
