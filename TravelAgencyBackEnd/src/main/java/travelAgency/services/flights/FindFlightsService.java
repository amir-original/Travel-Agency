package travelAgency.services.flights;

import travelAgency.domain.Flight;
import travelAgency.domain.FlightPlan;

import java.util.List;
import java.util.Optional;

public interface FindFlightsService {
    List<Flight> getFlights();
    List<Flight> findFlights(FlightPlan flightPlan);
    Optional<Flight> findFlight(FlightPlan flightPlan);

    boolean isExistThisFlight(FlightPlan flightPlan);
    void checkingTheExistenceFlight(FlightPlan flightPlan);
}
