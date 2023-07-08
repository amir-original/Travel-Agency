package travelAgency.model.flight;

import org.jetbrains.annotations.NotNull;
import travelAgency.model.city.City;

import java.time.LocalDate;

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
