package travelAgency.services.flights;

import travelAgency.domain.booking.BookingInformation;
import travelAgency.domain.exceptions.FullyBookedException;
import travelAgency.domain.exceptions.NotEnoughCapacityException;
import travelAgency.repository.booking.BookingListRepository;
import travelAgency.repository.flight.FlightRepository;

public class FlightAvailabilityImpl implements FlightAvailability {

    private static final int NO_SEATS = 0;
    private final FlightRepository flightRepository;
    private final BookingListRepository bookingLists;

    public FlightAvailabilityImpl(FlightRepository flightRepository,
                                  BookingListRepository bookingLists) {
        this.flightRepository = flightRepository;
        this.bookingLists = bookingLists;
    }

    @Override
    public int totalCapacity(String flightNumber) {
        return getAvailableSeats(flightNumber, bookingLists.getBookedSeats(flightNumber));
    }

    @Override
    public void checkFlight(BookingInformation bi) {
        flightRepository.checkExistenceFlightWith(bi.flightNumber());
        checkAvailability(bi);
    }

    private void checkAvailability(BookingInformation bi) {
        if (isSoldOutAllSeats(bi.flightNumber()))
            throw new FullyBookedException();
        if (!isAvailableSeatsFor(bi))
            throw new NotEnoughCapacityException();
    }


    private boolean isSoldOutAllSeats(String flightNumber) {
        return getAvailableSeats(flightNumber, bookingLists.getBookedSeats(flightNumber)) <= NO_SEATS;
    }

    private boolean isAvailableSeatsFor(BookingInformation bi) {
        final String flightNumber = bi.flightNumber();
        final int totalBookingSeats = getTotalBookingSeats(bi);
        return getAvailableSeats(flightNumber, totalBookingSeats) >= NO_SEATS;
    }

    private int getTotalBookingSeats(BookingInformation bi) {
        return bi.numberOfTickets() + bookingLists.getBookedSeats(bi.flightNumber());
    }

    private int getAvailableSeats(String flightNumber, int bookedSeats) {
        return flightRepository.numberOfSeats(flightNumber) - bookedSeats;
    }
}
