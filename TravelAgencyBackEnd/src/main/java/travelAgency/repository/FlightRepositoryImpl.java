package travelAgency.repository;

import travelAgency.domain.Flight;

import java.util.List;
import java.util.Optional;

public class FlightRepositoryImpl implements FlightRepository {

    @Override
    public List<Flight> getFlights() {
        return null;
    }

    @Override
    public List<Flight> findFlights(Flight flightInfo) {
        return null;
    }

    @Override
    public Optional<Flight> findFlight(Flight flightInfo) {
        return Optional.empty();
    }
}
