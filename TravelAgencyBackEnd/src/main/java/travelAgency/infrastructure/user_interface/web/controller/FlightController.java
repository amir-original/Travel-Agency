package travelAgency.infrastructure.user_interface.web.controller;

import travelAgency.application.dto.FlightPlanRequest;
import travelAgency.model.flight.Flight;
import travelAgency.model.flight.FlightPlan;
import travelAgency.application.dto.FlightDto;
import travelAgency.application.use_case.FindFlightService;
import travelAgency.application.use_case.SearchReservationService;

import java.util.List;

public class FlightController implements FlightOperations {

    private final FindFlightService findFlightService;
    private final SearchReservationService searchReservationService;

    public FlightController(FindFlightService findFlightService, SearchReservationService searchReservationService) {
        this.findFlightService = findFlightService;
        this.searchReservationService = searchReservationService;
    }

    public List<FlightDto> searchFlights(FlightPlanRequest flightPlan) {
        return findFlightService.searchFlights(flightPlan);
    }

    public FlightDto findFlight(String flightNumber) {
        return findFlightService.findFlight(flightNumber);
    }

    public int availableSeats(String flightNumber){
        return searchReservationService.availableSeats(flightNumber);
    }


}
