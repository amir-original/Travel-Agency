package travelAgency.services.flights;

import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightPlan;
import travelAgency.domain.exceptions.FlightNumberNotFoundException;
import travelAgency.repository.flight.FlightRepository;

import java.util.List;

public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final SearchFlightEngine searchFlightEngine;
    private List<Flight> flights;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
        getFlights();
        this.searchFlightEngine = new SearchFlightEngine(flights);
    }

    private void getFlights() {
        this.flights = flightRepository.flights();
    }


    @Override
    public List<Flight> search(FlightPlan searchFlightPlan) {
        return searchFlightEngine.search(searchFlightPlan);
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

}
