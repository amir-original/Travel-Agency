package travelAgency.repository;

import travelAgency.domain.Flight;
import travelAgency.domain.FlightInformation;

import java.util.List;
import java.util.Optional;

public interface FlightRepository {

    List<Flight> getFlights();

    List<Flight> findFlights(FlightInformation flightSpec);

    Optional<Flight> findFlight(FlightInformation flightSpec);
}
