package travelAgency.services.flights;

import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightPlan;

import java.util.List;

public interface FlightService {
    List<Flight> searchFlights(FlightPlan searchFlightPlan);
    Flight findFlight(String flightNumber);
    int availableSeats(String flightNumber);
}
