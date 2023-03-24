package travelAgency.services.flights;

import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightPlan;

import java.util.List;

public class SearchFlightEngine {

    private final FlightService findFlightsService;

    private List<Flight> flights;

    public SearchFlightEngine(FlightService findFlightsService) {
        this.findFlightsService = findFlightsService;
        getFlights();
    }

    private void getFlights() {
        this.flights = findFlightsService.getFlights();
    }

    public List<Flight> search(FlightPlan searchFlightPlan){
       return flights.stream()
               .filter(flight -> flight.matches(searchFlightPlan))
               .toList();
    }

}
