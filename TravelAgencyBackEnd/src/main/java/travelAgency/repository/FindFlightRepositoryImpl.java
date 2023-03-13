package travelAgency.repository;

import travelAgency.domain.Flight;
import travelAgency.domain.FlightPlan;

import java.util.List;
import java.util.Optional;

public class FindFlightRepositoryImpl implements FindFlightRepository {

    @Override
    public List<Flight> getFlights() {
        return null;
    }

    @Override
    public List<Flight> findFlights(FlightPlan flightSpec) {
        return null;
    }

    @Override
    public Optional<Flight> findFlight(FlightPlan flightSpec) {
        return Optional.empty();
    }
}
