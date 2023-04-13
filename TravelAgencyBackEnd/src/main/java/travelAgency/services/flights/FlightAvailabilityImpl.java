package travelAgency.services.flights;

import travelAgency.domain.booking.BookingInformation;
import travelAgency.services.bookingList.BookingList;
import travelAgency.repository.booking.BookingListRepository;
import travelAgency.repository.flight.FlightRepository;

public class FlightAvailabilityImpl implements FlightAvailability {
    private final FlightRepository flights;
    private final BookingList bookingList;

    public FlightAvailabilityImpl(FlightRepository flights,
                                  BookingListRepository bookingLists) {
        this.flights = flights;
        bookingList = new BookingList(bookingLists.getAllBookings());
    }

    @Override
    public void checkFlight(BookingInformation bi) {
        bi.checkExistenceFlight(flights.flights());
        bookingList.checkAvailability(bi);
    }

}
