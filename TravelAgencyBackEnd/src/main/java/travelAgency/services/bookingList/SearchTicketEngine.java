package travelAgency.services.bookingList;

import travelAgency.domain.FlightTicket;
import travelAgency.domain.exceptions.NotFoundAnyBookingFlightException;
import travelAgency.repository.BookingListRepository;

import java.time.LocalDate;

public class SearchTicketEngine {
    private final BookingListService bookingListService;


    public SearchTicketEngine(BookingListService bookingListService) {
        this.bookingListService = bookingListService;
    }

    public FlightTicket search(String flightName, String passengerFirstName, LocalDate PassengerBirthday) {
        return bookingListService.getAllTickets().stream()
                .filter(booking -> isFindTicketBy(flightName, passengerFirstName, PassengerBirthday, booking))
                .findFirst()
                .orElseThrow(NotFoundAnyBookingFlightException::new);
    }

    private boolean isFindTicketBy(String flightName, String passengerFirstName,
                                   LocalDate PassengerBirthday, FlightTicket booking) {

        return booking.flight().getSerialNumber().equals(flightName) &&
                booking.passenger().fName().equals(passengerFirstName) &&
                booking.passenger().birthday().equals(PassengerBirthday);
    }
}
