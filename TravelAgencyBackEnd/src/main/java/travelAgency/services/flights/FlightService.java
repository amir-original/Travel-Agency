package travelAgency.services.flights;

import travelAgency.domain.Flight;

import java.util.List;

public interface FlightService {

    void add(Flight flight);
    void add(List<Flight> flights);
    void delete(String flightNumber);
}
