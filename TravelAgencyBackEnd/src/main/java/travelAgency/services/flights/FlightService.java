package travelAgency.services.flights;

import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightPlan;

import java.util.List;

public interface FlightService {
    List<Flight> search(FlightPlan searchFlightPlan);
    Flight findFlight(String flightNumber);
    int availableSeats(String flightNumber);
}
