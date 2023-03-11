package travelAgency.repository;

import travelAgency.domain.Flight;
import travelAgency.domain.FlightInformation;

import java.util.List;
import java.util.Optional;

public class FlightRepositoryImpl implements FlightRepository {

    @Override
    public List<Flight> getFlights() {
        return null;
    }

    @Override
    public List<Flight> findFlights(FlightInformation flightSpec) {
        return null;
    }

    @Override
    public Optional<Flight> findFlight(FlightInformation flightSpec) {
        return Optional.empty();
    }
}
