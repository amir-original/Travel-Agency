package travelAgency.infrastructure.user_interface.web.controller;

import travelAgency.model.flight.Flight;
import travelAgency.model.flight.FlightPlan;
import travelAgency.application.dto.FlightDto;
import travelAgency.application.flight.FlightListService;
import travelAgency.application.reservation.ReservationListService;

import java.util.List;

public class FlightController implements FlightOperations {

    private final FlightListService flightListService;
    private final ReservationListService reservationListService;

    public FlightController(FlightListService flightListService, ReservationListService reservationListService) {
        this.flightListService = flightListService;
        this.reservationListService = reservationListService;
    }

    public List<Flight> searchFlights(FlightPlan flightPlan) {
        return flightListService.searchFlights(flightPlan);
    }

    public FlightDto findFlight(String flightNumber) {
        return flightListService.findFlight(flightNumber);
    }

    public int availableSeats(String flightNumber){
        return reservationListService.availableSeats(flightNumber);
    }


}
