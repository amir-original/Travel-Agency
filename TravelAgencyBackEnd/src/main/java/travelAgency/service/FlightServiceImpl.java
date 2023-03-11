package travelAgency.service;

import travelAgency.domain.Flight;
import travelAgency.domain.FlightInformation;
import travelAgency.domain.exceptions.NotFindAnyFlightException;
import travelAgency.repository.FlightRepository;

import java.util.List;
import java.util.Optional;

public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public List<Flight> getFlights() {
        return flightRepository.getFlights();
    }

    @Override
    public List<Flight> findFlights(FlightInformation flightSpec) {
        return flightRepository.findFlights(flightSpec);
    }

    @Override
    public Optional<Flight> findFlight(FlightInformation flightSpec) {
        return flightRepository.findFlight(flightSpec);
    }

    @Override
    public void checkingTheExistenceThisFlight(FlightInformation flightSpec) {
        if (isNotExistThisFlight(flightSpec)) throw new NotFindAnyFlightException();
    }

    @Override
    public boolean isExistThisFlight(FlightInformation flightSpec) {
        return isNotEmpty(findFlights(flightSpec));
    }

    private boolean isNotExistThisFlight(FlightInformation flightSpec) {
        return !isExistThisFlight(flightSpec);
    }

    private boolean isNotEmpty(List<Flight> objects) {
        return !objects.isEmpty();
    }
}
