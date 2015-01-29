package org.geoint.acetate.data.unit;

/**
 * Temperature unit.
 */
public enum TempUnit {

    /**
     * Celsius
     */
    C {

                @Override
                public double toFahrenheight(double c) {
                    return c * C2F + CF;
                }

                @Override
                public double toKelvin(double c) {
                    return c + CK;
                }

                @Override
                public double toCelsius(double c) {
                    return c;
                }

                @Override
                public double convert(double d, TempUnit u) {
                    return u.toCelsius(d);
                }
            },
    /**
     * Fahrenheight
     */
    F {
                @Override
                public double toFahrenheight(double f) {
                    return f;
                }

                @Override
                public double toCelsius(double f) {
                    return (f - CF) * F2C;
                }

                @Override
                public double toKelvin(double f) {
                    return ((f - CF) / C2F) + CK;
                }

                @Override
                public double convert(double d, TempUnit u) {
                    return u.toFahrenheight(d);
                }
            },
    /**
     * Kelvin
     */
    K {
                @Override
                public double toKelvin(double k) {
                    return k;
                }

                @Override
                public double toCelsius(double k) {
                    return k - CK;
                }

                @Override
                public double toFahrenheight(double k) {
                    return ((k - CK) * C2F) + CF;
                }

                @Override
                public double convert(double d, TempUnit u) {
                    return u.toKelvin(d);
                }
            };

    static final double C2F = 1.8;
    static final double F2C = .5555555555555555555556;
    static final double CK = 273.15;
    static final int CF = 32;

    public double convert(double sourceTemp, TempUnit unit) {
        throw new AbstractMethodError();
    }

    public double toCelsius(double c) {
        throw new AbstractMethodError();
    }

    public double toKelvin(double c) {
        throw new AbstractMethodError();
    }

    public double toFahrenheight(double c) {
        throw new AbstractMethodError();
    }
}
