package travelAgency.service;

import travelAgency.domain.Flight;
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
    public List<Flight> findFlights(Flight flightInfo) {
        return flightRepository.findFlights(flightInfo);
    }

    @Override
    public Optional<Flight> findFlight(Flight flightInfo) {
        return flightRepository.findFlight(flightInfo);
    }

    @Override
    public void checkingTheExistenceThisFlight(Flight flight) {
        if (isNotExistThisFlight(flight)) throw new NotFindAnyFlightException();
    }

    @Override
    public boolean isExistThisFlight(Flight flight) {
        return isNotEmpty(findFlights(flight));
    }

    private boolean isNotExistThisFlight(Flight flight) {
        return !isExistThisFlight(flight);
    }

    private boolean isNotEmpty(List<Flight> objects) {
        return !objects.isEmpty();
    }
}
