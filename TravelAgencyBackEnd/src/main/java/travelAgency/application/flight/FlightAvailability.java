package travelAgency.application.flight;

import travelAgency.model.reservation.Reservation;
import travelAgency.exceptions.FullyBookedException;
import travelAgency.exceptions.NotEnoughCapacityException;
import travelAgency.model.flight.Flight;
import travelAgency.application.reservation.ReservationListService;

public final class FlightAvailability {

    private static final int NO_AVAILABLE_SEATS = 0;
    private final ReservationListService bookingLists;

    public FlightAvailability(ReservationListService reservationList) {
        this.bookingLists = reservationList;
    }

    public void ensureCanBooking(Reservation reservation) {
        final Flight flight = reservation.flight();
        flight.validateScheduleNotInPast();
        checkFlightCapacity(reservation);
    }

    public void checkFlightCapacity(Reservation resInfo) {
        if (isSoldOutAllSeats(resInfo))
            throw new FullyBookedException();
        if (hasNotEnoughCapacity(resInfo))
            throw new NotEnoughCapacityException();
    }

    private boolean isSoldOutAllSeats(Reservation resInfo) {
        return getAvailableSeats(resInfo) == NO_AVAILABLE_SEATS;
    }

    private boolean hasNotEnoughCapacity(Reservation resInfo) {
        return !hasEnoughCapacity(resInfo);
    }

    private boolean hasEnoughCapacity(Reservation resInfo) {
        return getAvailableSeats(resInfo) >= resInfo.travelers();
    }

    private int getAvailableSeats(Reservation resInfo) {
        return bookingLists.availableSeats(resInfo.flightNumber());
    }
}
