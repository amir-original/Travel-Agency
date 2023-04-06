package travelAgency.repository.flight;

import travelAgency.domain.exceptions.FullyBookedException;
import travelAgency.domain.exceptions.NotEnoughCapacityException;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightPlan;

import java.util.List;
import java.util.Optional;

public interface FlightRepository {

    void addFlight(Flight flight);

    void addFlights(List<Flight> flights);

    void deleteFlight(String serialNumber);

    Optional<Flight> findFlight(String flightNumber);

    List<Flight> findFlights(FlightPlan flightPlan);

    List<Flight> flights();

    int numberOfSeats(String flightNumber);

    void truncate();

    void checkExistenceFlightWith(String flightNumber);
}
