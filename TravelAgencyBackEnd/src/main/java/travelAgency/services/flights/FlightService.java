package travelAgency.services.flights;

import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightPlan;

import java.util.List;

public interface FlightService {
    List<Flight> search(FlightPlan searchFlightPlan);
    List<Flight> findFlights(FlightPlan flightPlan);
    Flight findFlight(String flightNumber);
    void checkExistenceFlightWith(String flightNumber);
    boolean isExistThisFlight(FlightPlan flightPlan);

    // TODO (flight capacity, seats number)
}
