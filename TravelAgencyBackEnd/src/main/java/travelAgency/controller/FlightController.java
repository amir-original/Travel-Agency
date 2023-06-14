package travelAgency.controller;

import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightPlan;
import travelAgency.domain.reservation.FlightDto;
import travelAgency.services.flight.FlightListService;
import travelAgency.services.reservation.ReservationListService;

import java.util.List;

public class FlightController {

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

    public int getAvailableSeats(String flightNumber){
        return reservationListService.getAvailableSeats(flightNumber);
    }


}
