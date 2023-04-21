package travelAgency.domain.flight;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.exceptions.PastFlightScheduleException;

import java.time.LocalDate;

import static java.time.LocalDate.now;

public record FlightSchedule(@NotNull LocalDate departure, @NotNull LocalDate arrival) {
}
