package travelAgency.domain.flight;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.city.City;

import java.time.LocalDate;
import java.util.List;

public record FlightPlan(@NotNull FlightLocation location,
                         @NotNull FlightSchedule schedule) {

    public void validateSchedule() {
        schedule.validate();
    }

    public List<Flight> search(List<Flight> flights) {
        return flights
                .stream()
                .filter(flight -> flight.match(this))
                .toList();
    }

    public City from() {
        return location.from();
    }

    public City to() {
        return location.to();
    }

    public LocalDate departure() {
        return schedule.departure();
    }

    public LocalDate arrival() {
        return schedule.arrival();
    }
}
