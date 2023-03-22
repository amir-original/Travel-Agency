package travelAgency.domain.flight;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.city.City;

public record FlightLocation(@NotNull City from, @NotNull City to) {
}
