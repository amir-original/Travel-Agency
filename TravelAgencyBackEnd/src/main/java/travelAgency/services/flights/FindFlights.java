package travelAgency.services.flights;

import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightPlan;
import travelAgency.domain.exceptions.FlightNumberNotFoundException;
import travelAgency.repository.flight.FindFlightRepository;

import java.util.List;

public class FindFlights implements FindFlightsService {

    private final FindFlightRepository findFlightRepository;
    private List<Flight> flights;

    public FindFlights(FindFlightRepository findFlightRepository) {
        this.findFlightRepository = findFlightRepository;
        initFlights();
    }

    private void initFlights() {
       flights = findFlightRepository.getFlights();
    }

    @Override
    public List<Flight> getFlights() {
        return flights;
    }

    @Override
    public List<Flight> findFlights(FlightPlan flightPlan) {
        return findFlightRepository.findFlights(flightPlan);
    }


    @Override
    public Flight findFlight(String flightNumber) {
        return findFlightRepository.findFlight(flightNumber)
                .orElseThrow(FlightNumberNotFoundException::new);
    }

    @Override
    public void checkExistenceFlightWith(String flightNumber) {
        findFlightRepository.checkExistenceFlightWith(flightNumber);
    }

    @Override
    public boolean isExistThisFlight(FlightPlan flightPlan) {
        return flights.stream().anyMatch(flight -> flight.matches(flightPlan));
    }

}
