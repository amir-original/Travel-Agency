package travelAgency.services.bookingList;

import travelAgency.domain.booking.BookingInformation;
import travelAgency.domain.booking.Reservation;
import travelAgency.domain.exceptions.FullyBookedException;
import travelAgency.domain.exceptions.NotEnoughCapacityException;
import travelAgency.domain.exceptions.NotFoundAnyFlightException;

import java.util.List;

import static java.util.stream.Stream.of;

public class BookingList {

    private final List<Reservation> bookings;
    private static final int FULL_CAPACITY = 0;

    public BookingList(List<Reservation> bookings) {
        this.bookings = bookings;
    }

    public int availableSeats(String flightNumber) {
        return getAvailableSeats(flightNumber, getBookedSeats(flightNumber));
    }

    public void checkAvailability(BookingInformation bi) {
        if (isSoldOutAllSeats(bi.flightNumber()))
            throw new FullyBookedException();
        if (!isAvailableSeatsFor(bi))
            throw new NotEnoughCapacityException();
    }

    public boolean isSoldOutAllSeats(String flightNumber) {
        return getAvailableSeats(flightNumber, getBookedSeats(flightNumber)) <= FULL_CAPACITY;
    }

    public boolean isAvailableSeatsFor(BookingInformation bi) {
        return getAvailableSeats(bi.flightNumber(), getTotalBookingSeats(bi)) >= FULL_CAPACITY;
    }

    public int getTotalBookingSeats(BookingInformation bi) {
        return bi.numberOfTickets() + getBookedSeats(bi.flightNumber());
    }

    public int getBookedSeats(String flightNumber){
        return bookings.stream()
                .filter(flightTicket -> flightTicket.canMatchWith(flightNumber))
                .mapToInt(Reservation::travelers)
                .sum();
    }

    public int getAvailableSeats(String flightNumber, int bookedSeats) {
        return getNumberOfSeats(flightNumber) - bookedSeats;
    }

    public int getNumberOfSeats(String flightNumber) {
        return bookings.stream()
                .flatMap(flightTicket -> of(flightTicket.flight()))
                .filter(flight -> flight.matches(flightNumber))
                .findFirst()
                .orElseThrow(NotFoundAnyFlightException::new)
                .totalCapacity();
    }
}
