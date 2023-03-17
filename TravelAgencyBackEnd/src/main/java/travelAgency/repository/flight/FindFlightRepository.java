package travelAgency.repository.flight;

import travelAgency.domain.Flight;
import travelAgency.domain.FlightPlan;

import java.util.List;
import java.util.Optional;

public interface FindFlightRepository {

    List<Flight> getFlights();

    List<Flight> findFlights(FlightPlan flightPlan);

    Optional<Flight> findFlight(String flightNumber);

    void checkExistenceFlightWith(FlightPlan flightPlan);
}