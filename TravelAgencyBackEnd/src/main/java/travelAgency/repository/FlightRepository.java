package travelAgency.repository;

import travelAgency.domain.Flight;

import java.util.List;

public interface FlightRepository {

    List<Flight> getFlights();

    List<Flight> findFlights(Flight flightInfo);
}
