package travelAgency.services.flight;

import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.SeatAvailabilityChecker;
import travelAgency.domain.reservation.BookingInformation;
import travelAgency.repository.booking.BookingListRepository;
import travelAgency.repository.flight.FlightRepository;

public class FlightAvailabilityImpl implements FlightAvailability {

    private final FlightRepository flights;
    private final BookingListRepository bookingLists;

    public FlightAvailabilityImpl(FlightRepository flights, BookingListRepository bookingLists) {
        this.flights = flights;
        this.bookingLists = bookingLists;
    }

    @Override
    public void checkFlightPreBooking(BookingInformation bi) {
        final Flight flight = bi.findFlight(flights.flights());
        flight.validateSchedule();
        checkFlightCapacity(bi);
    }

    private void checkFlightCapacity(BookingInformation bi) {
        new SeatAvailabilityChecker(bi).checkCapacity(bookingLists.getAllBookings());
    }

}
