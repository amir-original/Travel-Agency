package travelAgency.domain;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.city.City;

import java.time.LocalDate;

public record FlightPlan(@NotNull FlightLocation location,@NotNull FlightSchedule schedule) {

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
