package travelAgency.domain;

import java.time.LocalDate;

public record FlightSchedule(LocalDate departure,LocalDate arrival) {
}
