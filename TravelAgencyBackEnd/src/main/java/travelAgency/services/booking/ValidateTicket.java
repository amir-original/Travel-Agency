package travelAgency.services.booking;

import travelAgency.domain.FlightTicket;
import travelAgency.services.flights.FindFlightsService;

public class ValidateTicket {

    private final FindFlightsService findFlightsService;

    public ValidateTicket(FindFlightsService findFlightsService) {
        this.findFlightsService = findFlightsService;
    }

    public void validate(FlightTicket ticket) {
        checkTicket(ticket);
        findFlightsService.checkingTheExistenceFlight(ticket.flightPlan());
    }

    private void checkTicket(FlightTicket ticket) {
        ticket.check();
    }
}
