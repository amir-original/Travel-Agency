package travelAgency.services.bookingList;

import travelAgency.domain.booking.Reservation;
import travelAgency.domain.exceptions.ReservationNotFoundException;
import travelAgency.domain.flight.Flight;
import travelAgency.repository.booking.BookingListRepository;

import java.time.LocalDate;
import java.util.List;

public class BookingListServiceImpl implements BookingListService {

    private final BookingListRepository bookings;
    private final SearchTicketEngine searchEngine;


    public BookingListServiceImpl(BookingListRepository bookings) {
        this.bookings = bookings;
        this.searchEngine = new SearchTicketEngine(getAllReservations());
    }

    @Override
    public Reservation search(String flightNumber, String passengerFirstName, LocalDate passengerBirthday) {
        return searchEngine.search(flightNumber, passengerFirstName, passengerBirthday);
    }

    @Override
    public void cancel(String ticketNumber) {
        bookings.cancel(ticketNumber);
    }

    @Override
    public int getBookedSeats(String flightNumber) {
        return findFlight(flightNumber).getBookedSeats(getAllReservations());
    }

    @Override
    public List<Reservation> getAllReservations() {
        return bookings.getAllBookings();
    }

    public Flight findFlight(String flightNumber) {
        return bookings
                .findBookingByFlightNumber(flightNumber)
                .orElseThrow(ReservationNotFoundException::new)
                .flight();
    }

}
