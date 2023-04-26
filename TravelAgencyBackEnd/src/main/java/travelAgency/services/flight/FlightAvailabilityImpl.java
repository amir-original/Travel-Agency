package travelAgency.services.flight;

import travelAgency.domain.exceptions.FullyBookedException;
import travelAgency.domain.exceptions.NotEnoughCapacityException;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.reservation.ReservationInformation;
import travelAgency.services.reservation.ReservationListService;

public class FlightAvailabilityImpl implements FlightAvailability {

    private static final int NO_AVAILABLE_SEATS = 0;

    private final FlightListService flights;
    private final ReservationListService bookingLists;

    public FlightAvailabilityImpl(FlightListService flights, ReservationListService reservationList) {
        this.flights = flights;
        this.bookingLists = reservationList;
    }

    @Override
    public void canBooking(ReservationInformation resInfo) {
        final Flight flight = flights.findFlight(resInfo.flightNumber());
        flight.validateScheduleNotInPast();
        checkFlightCapacity(resInfo);
    }

    public void checkFlightCapacity(ReservationInformation resInfo) {
        if (isSoldOutAllSeats(resInfo))
            throw new FullyBookedException();
        if (!hasEnoughCapacity(resInfo))
            throw new NotEnoughCapacityException();
    }

    private boolean isSoldOutAllSeats(ReservationInformation resInfo) {
        return getAvailableSeats(resInfo) == NO_AVAILABLE_SEATS;
    }

    private boolean hasEnoughCapacity(ReservationInformation resInfo) {
        return getAvailableSeats(resInfo) >= resInfo.numberOfTickets();
    }

    private int getAvailableSeats(ReservationInformation resInfo) {
        return bookingLists.getAvailableSeats(resInfo.flightNumber());
    }

}
