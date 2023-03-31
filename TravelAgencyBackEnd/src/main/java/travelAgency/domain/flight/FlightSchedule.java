package travelAgency.domain.flight;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.exceptions.FlightScheduleException;

import java.time.LocalDate;

public record FlightSchedule(@NotNull LocalDate departure, @NotNull LocalDate arrival) {
    public void check() {
        final LocalDate now = LocalDate.now();
        if (departure.isBefore(now) || arrival.isBefore(now)) {
            throw new FlightScheduleException();
        }
    }
}
