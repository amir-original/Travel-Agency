package travelAgency.service;

import travelAgency.domain.Flight;

import java.util.List;
import java.util.Optional;

public interface FlightService {
    List<Flight> getFlights();
    List<Flight> findFlights(Flight flightInfo);
    Optional<Flight> findFlight(Flight flightInfo);

    boolean isExistThisFlight(Flight flight);
    void checkingTheExistenceThisFlight(Flight flight);
}
