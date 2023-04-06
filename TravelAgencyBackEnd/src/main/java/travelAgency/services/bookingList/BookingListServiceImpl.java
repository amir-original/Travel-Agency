package travelAgency.services.bookingList;

import travelAgency.domain.booking.BookingInformation;
import travelAgency.domain.booking.FlightTicket;
import travelAgency.domain.exceptions.NotFoundAnyBookingFlightException;
import travelAgency.repository.booking.BookingListRepository;
import travelAgency.services.BookingFlightTicket;

import java.time.LocalDate;
import java.util.List;

public class BookingListServiceImpl implements BookingListService {

    private final BookingListRepository bookings;
    private final SearchTicketEngine searchEngine;
    private final BookingFlightTicket bookingFlightTicket;


    public BookingListServiceImpl(BookingListRepository bookings,
                                  BookingFlightTicket bookingFlightTicket) {
        this.bookings = bookings;
        this.bookingFlightTicket = bookingFlightTicket;
        this.searchEngine = new SearchTicketEngine(getAllBookings());
    }


    @Override
    public FlightTicket book(BookingInformation bookingInformation) {
        return bookingFlightTicket.book(bookingInformation);
    }

    @Override
    public FlightTicket search(String flightNumber, String passengerFirstName, LocalDate passengerBirthday) {
       return searchEngine.search(flightNumber,passengerFirstName,passengerBirthday);
    }

    @Override
    public void cancel(String ticketNumber) {
        bookings.cancel(ticketNumber);
    }

    @Override
    public int getBookedSeats(String flightNumber) {
        return bookings.getBookedSeats(flightNumber);
    }

    @Override
    public List<FlightTicket> getAllBookings() {
        return bookings.getAllBookings();
    }

    @Override
    public FlightTicket findBooking(String ticketNumber) {
        return bookings.findBooking(ticketNumber)
                .orElseThrow(NotFoundAnyBookingFlightException::new);
    }

}
