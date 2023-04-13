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
    private final BookingList bookingList;


    public BookingListServiceImpl(BookingListRepository bookings) {
        this.bookings = bookings;
        this.searchEngine = new SearchTicketEngine(getAllBookings());
        bookingList = new BookingList(bookings.getAllBookings());
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
        return bookingList.getBookedSeats(flightNumber);
    }

    @Override
    public int availableSeats(String flightNumber) {
        return bookingList.availableSeats(flightNumber);
    }

    @Override
    public List<FlightTicket> getAllBookings() {
        return bookings.getAllBookings();
    }

}
