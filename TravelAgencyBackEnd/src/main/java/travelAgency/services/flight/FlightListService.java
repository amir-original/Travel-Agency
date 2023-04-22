package travelAgency.services.flight;

import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightPlan;

import java.util.List;

public interface FlightListService {
    List<Flight> searchFlights(FlightPlan searchFlightPlan);
    Flight findFlight(String flightNumber);
    int getTotalCapacity(String flightNumber);
}
