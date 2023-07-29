package travelAgency.model.flight;

import org.jetbrains.annotations.NotNull;

public record FlightLocation(@NotNull City from, @NotNull City to) {

    public FlightLocation {
        validate(from,to);
    }

    public static FlightLocation with(City from, City to) {
        return new FlightLocation(from,to);
    }

    public void validate(City from,City to) {
        if (from.equals(to))
            throw new IllegalArgumentException("Flights location must not be the same!");
    }
}
