package travelAgency.services.flights;

import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightPlan;

import java.util.List;

public interface FindFlightsService {
    List<Flight> getFlights();
    List<Flight> findFlights(FlightPlan flightPlan);
    Flight findFlight(String flightNumber);
    void checkExistenceFlightWith(String flightNumber);
    boolean isExistThisFlight(FlightPlan flightPlan);
}
