package travelAgency.services.flights;

import travelAgency.domain.Flight;
import travelAgency.domain.FlightPlan;
import travelAgency.domain.exceptions.FlightNumberNotFoundException;
import travelAgency.repository.flight.FindFlightRepository;

import java.util.List;

public class FindFlights implements FindFlightsService {

    private final FindFlightRepository flightRepository;
    private List<Flight> flights;

    public FindFlights(FindFlightRepository flightRepository) {
        this.flightRepository = flightRepository;
        initFlights();
    }

    private void initFlights() {
       flights = flightRepository.getFlights();
    }

    @Override
    public List<Flight> getFlights() {
        return flights;
    }

    @Override
    public List<Flight> findFlights(FlightPlan flightPlan) {
        return flightRepository.findFlights(flightPlan);
    }


    @Override
    public Flight findFlight(String flightNumber) {
        return flightRepository.findFlight(flightNumber)
                .orElseThrow(FlightNumberNotFoundException::new);
    }

    @Override
    public void checkExistenceFlightWith(String flightNumber) {
        flightRepository.checkExistenceFlightWith(flightNumber);
    }

    @Override
    public boolean isExistThisFlight(FlightPlan flightPlan) {
        return flights.stream().anyMatch(flight -> flight.matches(flightPlan));
    }

}
