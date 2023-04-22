package travelAgency.services.flight;

import travelAgency.domain.exceptions.FullyBookedException;
import travelAgency.domain.exceptions.NotEnoughCapacityException;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightScheduleValidator;
import travelAgency.domain.reservation.ReservationInformation;
import travelAgency.services.reservation.ReservationListService;

public class FlightAvailabilityImpl implements FlightAvailability {

    private static final int NO_AVAILABLE_SEATS = 0;

    private final FlightListService flights;
    private final ReservationListService bookingLists;

    public FlightAvailabilityImpl(FlightListService flights, ReservationListService bookingLists) {
        this.flights = flights;
        this.bookingLists = bookingLists;
    }

    @Override
    public void checkFlightPreBooking(ReservationInformation reservation) {
        final Flight flight = flights.findFlight(reservation.flightNumber());
        FlightScheduleValidator flightScheduleValidator = new FlightScheduleValidator(flight.schedule());

        flightScheduleValidator.validate();
        checkCapacity(reservation);
    }

    public void checkCapacity(ReservationInformation reservation) {
        if (isSoldOutAllSeats(reservation))
            throw new FullyBookedException();
        if (!hasEnoughCapacity(reservation))
            throw new NotEnoughCapacityException();
    }

    private boolean isSoldOutAllSeats(ReservationInformation reservation) {
        final int availableSeats = bookingLists.getAvailableSeats(reservation.flightNumber());
        return availableSeats == NO_AVAILABLE_SEATS;
    }

    private boolean hasEnoughCapacity(ReservationInformation reservation) {
        final int availableSeats = bookingLists.getAvailableSeats(reservation.flightNumber());
        return availableSeats >= reservation.numberOfTickets();
    }

}
