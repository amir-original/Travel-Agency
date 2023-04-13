package travelAgency.services.bookingList;

import travelAgency.domain.booking.Reservation;
import travelAgency.repository.booking.BookingListRepository;

import java.time.LocalDate;
import java.util.List;

public class BookingListServiceImpl implements BookingListService {

    private final BookingListRepository bookings;
    private final SearchTicketEngine searchEngine;
    private final BookingList bookingList;


    public BookingListServiceImpl(BookingListRepository bookings) {
        this.bookings = bookings;
        this.searchEngine = new SearchTicketEngine(getAllReservations());
        bookingList = new BookingList(bookings.getAllBookings());
    }

    @Override
    public Reservation search(String flightNumber, String passengerFirstName, LocalDate passengerBirthday) {
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
    public List<Reservation> getAllReservations() {
        return bookings.getAllBookings();
    }

}
