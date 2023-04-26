package travelAgency.domain.flight;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.exceptions.PastFlightScheduleException;

import java.time.LocalDate;

import static java.time.LocalDate.now;

public record FlightSchedule(@NotNull LocalDate departure, @NotNull LocalDate arrival) {
    private static final LocalDate NOW = now();

    public void validate() {
        if (isPassed())
            throw new PastFlightScheduleException();
    }

    boolean isPassed() {
        return departure().isBefore(NOW) || arrival().isBefore(NOW);
    }
}
