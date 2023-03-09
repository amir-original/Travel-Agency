package travelAgency.service;

import travelAgency.domain.Flight;
import travelAgency.domain.exceptions.NotFindAnyFlightException;
import travelAgency.repository.FlightRepository;

import java.util.List;

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
    public Flight findFlight(Flight flightInfo) {
        return null;
    }

    @Override
    public List<Flight> findFlights(Flight flightInfo) {
        return flightRepository.findFlights(flightInfo);
    }

    @Override
    public void checkingTheExistenceThisFlight(Flight flight) {
        if (isNotExistThisFlight(flight)) throw new NotFindAnyFlightException();
    }

    @Override
    public boolean isExistThisFlight(Flight flight) {
        return findFlights(flight).isEmpty();
    }

    private boolean isNotExistThisFlight(Flight flight) {
        return !isExistThisFlight(flight);
    }
}
