package travelAgency.repository;

import travelAgency.domain.Flight;

import java.util.List;
import java.util.Optional;

public interface FlightRepository {

    List<Flight> getFlights();

    List<Flight> findFlights(Flight flightInfo);

    Optional<Flight> findFlight(Flight flightInfo);
}
