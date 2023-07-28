package travelAgency.infrastructure.user_interface.web.controller;

import travelAgency.application.dto.FlightPlanRequest;
import travelAgency.model.flight.Flight;
import travelAgency.application.dto.FlightDto;
import travelAgency.model.flight.FlightPlan;

import java.util.List;

public interface FlightOperations {

     List<FlightDto> searchFlights(FlightPlanRequest flightPlan);

     FlightDto findFlight(String flightNumber);

     int availableSeats(String flightNumber);
}
