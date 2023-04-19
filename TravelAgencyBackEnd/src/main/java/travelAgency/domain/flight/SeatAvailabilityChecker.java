package travelAgency.domain.flight;

import travelAgency.domain.exceptions.FullyBookedException;
import travelAgency.domain.exceptions.NotEnoughCapacityException;
import travelAgency.domain.reservation.BookingInformation;
import travelAgency.domain.reservation.Reservation;

import java.util.List;

public class SeatAvailabilityChecker {

    private static final int NO_AVAILABLE_SEATS = -1;

    private final BookingInformation bookingInformation;

    public SeatAvailabilityChecker(BookingInformation bookingInformation) {
        this.bookingInformation = bookingInformation;
    }

    public void checkCapacity(List<Reservation> reservations) {
        if (isSoldOutAllSeats(reservations))
            throw new FullyBookedException();
        if (!isAvailableSeatsFor(reservations))
            throw new NotEnoughCapacityException();
    }

    private boolean isSoldOutAllSeats(List<Reservation> reservations) {
        return bookingInformation.getAvailableSeats(reservations) == NO_AVAILABLE_SEATS;
    }

    private boolean isAvailableSeatsFor(List<Reservation> reservations) {
        return bookingInformation.getAvailableSeatsAfterBooking(reservations) >= NO_AVAILABLE_SEATS;
    }
}
