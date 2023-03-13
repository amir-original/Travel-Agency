package travelAgency.repository;

import travelAgency.domain.Flight;
import travelAgency.domain.FlightPlan;

import java.util.List;
import java.util.Optional;

public interface FindFlightRepository {

    List<Flight> getFlights();

    List<Flight> findFlights(FlightPlan flightSpec);

    Optional<Flight> findFlight(FlightPlan flightSpec);
}
