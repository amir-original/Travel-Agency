package travelAgency.services.bookingList;

import travelAgency.domain.booking.FlightTicket;
import travelAgency.domain.exceptions.NotFoundAnyBookingFlightException;

import java.time.LocalDate;

public class SearchTicketEngine {
    private final BookingListService bookingListService;

    public SearchTicketEngine(BookingListService bookingListService) {
        this.bookingListService = bookingListService;
    }

    public FlightTicket search(String flightName, String passengerFirstName, LocalDate PassengerBirthday) {
        return bookingListService.getAllTickets().stream()
                .filter(booking -> booking.canMatchWith(flightName, passengerFirstName, PassengerBirthday))
                .findFirst()
                .orElseThrow(NotFoundAnyBookingFlightException::new);
    }

}
