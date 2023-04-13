package travelAgency.repository.flight;

import travelAgency.domain.flight.Flight;

import java.util.List;
import java.util.Optional;

public interface FlightRepository {

    void addFlight(Flight flight);

    void addFlights(List<Flight> flights);

    void deleteFlight(String serialNumber);

    Optional<Flight> findFlight(String flightNumber);

    List<Flight> flights();

    void truncate();
}
