package travelAgency.services.flights;

import travelAgency.domain.Flight;
import travelAgency.domain.FlightPlan;

import java.util.List;

public class SearchFlightEngine {

    private final FindFlightsService findFlightsService;

    public SearchFlightEngine(FindFlightsService findFlightsService) {
        this.findFlightsService = findFlightsService;
    }

    public List<Flight> search(FlightPlan searchFlightPlan){
       return findFlightsService.getFlights().stream()
               .filter(flight -> flight.matches(searchFlightPlan)).toList();
    }

}
