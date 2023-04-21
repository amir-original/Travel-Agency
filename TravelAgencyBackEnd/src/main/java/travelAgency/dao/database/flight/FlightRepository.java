package travelAgency.dao.database.flight;

import travelAgency.domain.flight.Flight;

import java.util.List;
import java.util.Optional;

public interface FlightRepository {

    void addFlight(Flight flight);

    void addFlights(List<Flight> flights);

    void deleteFlight(Flight flight);

    Optional<Flight> findFlight(String flightNumber);

    List<Flight> flights();

    void truncate();
}
