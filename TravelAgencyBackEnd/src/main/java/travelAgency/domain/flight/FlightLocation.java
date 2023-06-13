package travelAgency.domain.flight;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.city.City;

public record FlightLocation(@NotNull City from, @NotNull City to) {

    public FlightLocation {
        validate(from,to);
    }

    public void validate(City from,City to) {
        if (from.equals(to))
            throw new IllegalArgumentException("Flights location must not be the same!");
    }
}
