package travelAgency.services.flights;

import travelAgency.domain.Flight;
import travelAgency.domain.FlightPlan;
import travelAgency.domain.exceptions.NotFindAnyFlightException;
import travelAgency.repository.FindFlightRepository;

import java.util.List;
import java.util.Optional;

public class FindFlights implements FindFlightsService {

    private final FindFlightRepository flightRepository;

    public FindFlights(FindFlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public List<Flight> getFlights() {
        return flightRepository.getFlights();
    }

    @Override
    public List<Flight> findFlights(FlightPlan flightPlan) {
        return flightRepository.findFlights(flightPlan);
    }

    @Override
    public Optional<Flight> findFlight(FlightPlan flightPlan) {
        return flightRepository.findFlight(flightPlan);
    }

    @Override
    public void checkingTheExistenceFlight(FlightPlan flightPlan) {
        if (isNotExistThisFlight(flightPlan)) throw new NotFindAnyFlightException();
    }

    private boolean isNotExistThisFlight(FlightPlan flightPlan) {
        return !isExistThisFlight(flightPlan);
    }

    @Override
    public boolean isExistThisFlight(FlightPlan flightPlan) {
        return isNotEmpty(findFlights(flightPlan));
    }

    private boolean isNotEmpty(List<Flight> objects) {
        return !objects.isEmpty();
    }
}
