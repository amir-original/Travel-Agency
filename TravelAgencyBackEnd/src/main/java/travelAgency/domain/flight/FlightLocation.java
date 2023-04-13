package travelAgency.domain.flight;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.city.City;
import travelAgency.domain.exceptions.FlightLocationException;

public record FlightLocation(@NotNull City from, @NotNull City to) {

    public FlightLocation(@NotNull City from, @NotNull City to) {
        this.from = from;
        this.to = to;
        validate();
    }

    public void validate() {
        if (from.equals(to)) throw new FlightLocationException();
    }
}
