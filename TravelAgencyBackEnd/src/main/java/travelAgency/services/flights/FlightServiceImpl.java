package travelAgency.services.flights;

import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightPlan;
import travelAgency.domain.exceptions.FlightNumberNotFoundException;
import travelAgency.repository.flight.FindFlightRepository;

import java.util.List;

public class FlightServiceImpl implements FlightService {

    private final FindFlightRepository findFlightRepository;
    private final SearchFlightEngine searchFlightEngine;
    private List<Flight> flights;

    public FlightServiceImpl(FindFlightRepository findFlightRepository) {
        this.findFlightRepository = findFlightRepository;
        getFlights();
        this.searchFlightEngine = new SearchFlightEngine(flights);
    }

    private void getFlights() {
        this.flights = findFlightRepository.getFlights();
    }


    @Override
    public List<Flight> search(FlightPlan searchFlightPlan) {
        return searchFlightEngine.search(searchFlightPlan);
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

    public boolean isExistThisFlight(FlightPlan flightPlan) {
        return flights.stream().anyMatch(flight -> flight.matches(flightPlan));
    }

}
