package travelAgency.domain.flight;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.city.City;

import java.time.LocalDate;
import java.util.List;

public record FlightPlan(@NotNull FlightLocation location, @NotNull FlightSchedule schedule) {

    public static FlightPlan of(FlightLocation flightLocation, FlightSchedule flightSchedule) {
        return new FlightPlan(flightLocation,flightSchedule);
    }

    public boolean hasSameFlightPlan(FlightPlan flightPlan) {
        return flightPlan.equals(this);
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
