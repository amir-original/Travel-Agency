package travelAgency.service;

import travelAgency.domain.Flight;
import travelAgency.domain.FlightInformation;

import java.util.List;
import java.util.Optional;

public interface FlightService {
    List<Flight> getFlights();
    List<Flight> findFlights(FlightInformation flightSpec);
    Optional<Flight> findFlight(FlightInformation flightSpec);

    boolean isExistThisFlight(FlightInformation flightSpec);
    void checkingTheExistenceThisFlight(FlightInformation flightSpec);
}
