package org.geoint.acetate.lang.temp;

import java.time.Duration;
import java.util.Objects;

/**
 *
 */
public class TemperatureDuration {

    private final Temperature temperature;
    private final Duration duration;

    public TemperatureDuration(Temperature temp, Duration duration) {
        this.temperature = temp;
        this.duration = duration;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public Duration getDuration() {
        return duration;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.temperature);
        hash = 89 * hash + Objects.hashCode(this.duration);
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
        final TemperatureDuration other = (TemperatureDuration) obj;
        if (!Objects.equals(this.temperature, other.temperature)) {
            return false;
        }
        if (!Objects.equals(this.duration, other.duration)) {
            return false;
        }
        return true;
    }

}
