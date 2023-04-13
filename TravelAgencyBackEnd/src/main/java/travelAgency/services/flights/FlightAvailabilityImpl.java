package travelAgency.services.flights;

import travelAgency.domain.booking.BookingInformation;
import travelAgency.repository.booking.BookingListRepository;
import travelAgency.repository.flight.FlightRepository;

public class FlightAvailabilityImpl implements FlightAvailability {

    private final FlightRepository flights;
    private final BookingListRepository bookingLists;

    public FlightAvailabilityImpl(FlightRepository flights,
                                  BookingListRepository bookingLists) {
        this.flights = flights;
        this.bookingLists = bookingLists;
    }

    @Override
    public void checkFlightPreBooking(BookingInformation bi) {
        bi.checkExistenceFlight(flights.flights());
        bi.checkAvailability(bookingLists.getAllBookings());
    }

}
