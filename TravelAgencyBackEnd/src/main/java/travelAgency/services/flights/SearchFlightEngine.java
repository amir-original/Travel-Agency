package travelAgency.services.flights;

import travelAgency.domain.Flight;
import travelAgency.domain.FlightPlan;

import java.util.List;

public class SearchFlightEngine {

    private final FindFlightsService flightService;

    public SearchFlightEngine(FindFlightsService flightService) {
        this.flightService = flightService;
    }

    public List<Flight> search(FlightPlan searchFlightPlan){
       return flightService.getFlights().stream()
               .filter(flight -> flight.matches(searchFlightPlan)).toList();
    }

}
