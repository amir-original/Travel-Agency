package travelAgency.controller;

import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightPlan;
import travelAgency.services.flight.FlightListService;

import java.util.List;

public class FlightController {

    private final FlightListService flightListService;

    public FlightController(FlightListService flightListService) {
        this.flightListService = flightListService;
    }

    public List<Flight> searchFlights(FlightPlan flightPlan) {
        return flightListService.searchFlights(flightPlan);
    }

    public Flight findFlight(String flightNumber) {
        return flightListService.findFlight(flightNumber);
    }
}
