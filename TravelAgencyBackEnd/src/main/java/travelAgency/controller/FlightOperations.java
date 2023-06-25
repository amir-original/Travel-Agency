package travelAgency.controller;

import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightDto;
import travelAgency.domain.flight.FlightPlan;

import java.util.List;

public interface FlightOperations {

     List<Flight> searchFlights(FlightPlan flightPlan);

     FlightDto findFlight(String flightNumber);

     int availableSeats(String flightNumber);
}
