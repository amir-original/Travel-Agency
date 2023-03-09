package travelAgency.service;

import travelAgency.domain.Flight;

import java.util.List;

public interface FlightService {
    List<Flight> getFlights();
    List<Flight> findFlights(Flight flightInfo);
    Flight findFlight(Flight flightInfo);
    boolean isExistThisFlight(Flight flight);
    void checkingTheExistenceThisFlight(Flight flight);
}
