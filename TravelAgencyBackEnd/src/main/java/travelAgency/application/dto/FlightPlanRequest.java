package travelAgency.application.dto;

import java.time.LocalDate;

// flightDto
public record FlightPlanRequest(String from, String to, LocalDate departure, LocalDate arrival) {
}
