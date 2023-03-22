package travelAgency.repository.flight;

import travelAgency.domain.Flight;

import java.util.List;
import java.util.Optional;

public interface FlightRepository {
    void addFlight(Flight flight);
    void createFlights(List<Flight> flights);
    void deleteFlight(String serialNumber);
  /*  Optional<Flight> flight(String serialId);
    List<Flight> flights();*/
    void truncate();
}
