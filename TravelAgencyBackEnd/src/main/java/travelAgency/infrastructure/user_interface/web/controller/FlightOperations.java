package travelAgency.infrastructure.user_interface.web.controller;

import travelAgency.model.flight.Flight;
import travelAgency.application.dto.FlightDto;
import travelAgency.model.flight.FlightPlan;

import java.util.List;

public interface FlightOperations {

     List<Flight> searchFlights(FlightPlan flightPlan);

     FlightDto findFlight(String flightNumber);

     int availableSeats(String flightNumber);
}
