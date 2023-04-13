package travelAgency.domain.flight;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.exceptions.FlightScheduleMostNotBePastException;

import java.time.LocalDate;

import static java.time.LocalDate.now;

public record FlightSchedule(@NotNull LocalDate departure, @NotNull LocalDate arrival) {


    public FlightSchedule(@NotNull LocalDate departure, @NotNull LocalDate arrival) {
        this.departure = departure;
        this.arrival = arrival;
        validate();
    }

    public void validate() {
        if (isPassed())
            throw new FlightScheduleMostNotBePastException();
    }

    private boolean isPassed() {
        final LocalDate now = now();
        return departure.isBefore(now) || arrival.isBefore(now);
    }
}
