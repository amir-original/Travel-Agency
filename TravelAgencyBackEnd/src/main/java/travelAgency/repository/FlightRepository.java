package travelAgency.repository;

import travelAgency.domain.Flight;

public interface FlightRepository {
    void addFlight(Flight flight);
    void deleteFlight(Flight flight);
}
