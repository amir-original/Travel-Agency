package travelAgency.infrastructure.user_interface.web.controller;

import travelAgency.model.flight.Flight;
import travelAgency.model.flight.FlightPlan;
import travelAgency.application.dto.FlightDto;
import travelAgency.application.use_case.FindFlightService;
import travelAgency.application.use_case.FindReservationService;

import java.util.List;

public class FlightController implements FlightOperations {

    private final FindFlightService findFlightService;
    private final FindReservationService findReservationService;

    public FlightController(FindFlightService findFlightService, FindReservationService findReservationService) {
        this.findFlightService = findFlightService;
        this.findReservationService = findReservationService;
    }

    public List<Flight> searchFlights(FlightPlan flightPlan) {
        return findFlightService.searchFlights(flightPlan);
    }

    public FlightDto findFlight(String flightNumber) {
        return findFlightService.findFlight(flightNumber);
    }

    public int availableSeats(String flightNumber){
        return findReservationService.availableSeats(flightNumber);
    }


}
