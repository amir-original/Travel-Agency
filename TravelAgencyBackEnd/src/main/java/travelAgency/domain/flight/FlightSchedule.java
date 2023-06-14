package travelAgency.domain.flight;

import org.jetbrains.annotations.NotNull;
import travelAgency.exceptions.PastFlightScheduleException;

import java.time.LocalDate;

import static java.time.LocalDate.now;

public record FlightSchedule(@NotNull LocalDate departure, @NotNull LocalDate arrival) {
    private static final LocalDate NOW = now();

    public static FlightSchedule with(LocalDate departureDate, LocalDate arrivalDate) {
        return new FlightSchedule(departureDate,arrivalDate);
    }

    public void validate() {
        if (isPassed())
            throw new PastFlightScheduleException();
    }

    boolean isPassed() {
        return departure().isBefore(NOW) || arrival().isBefore(NOW);
    }
}
