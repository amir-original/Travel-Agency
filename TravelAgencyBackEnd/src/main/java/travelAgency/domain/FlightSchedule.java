package travelAgency.domain;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public record FlightSchedule(@NotNull LocalDate departure, @NotNull LocalDate arrival) {
}
