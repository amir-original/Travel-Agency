package travelAgency.services.flights;

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
        return getAvailableSeats(flightNumber, getBookedSeats(flightNumber));
    }

    @Override
    public void checkingFlight(String flightNumber, int newTravelers) {
        flightRepository.checkExistenceFlightWith(flightNumber);
        checkAvailability(flightNumber, newTravelers, getBookedSeats(flightNumber));
    }

    private void checkAvailability(String flightNumber, int newTravelers, int numberOfBooked) {
        if (isSoldOutAllSeats(flightNumber, numberOfBooked))
            throw new FullyBookedException();
        if (!isAvailableSeatsFor(flightNumber, numberOfBooked, newTravelers))
            throw new NotEnoughCapacityException();
    }

    private int getBookedSeats(String flightNumber) {
        return bookingLists.getBookedSeats(flightNumber);
    }


    private boolean isSoldOutAllSeats(String flightNumber, int numberOfBookedFlight) {
        return getAvailableSeats(flightNumber, numberOfBookedFlight) <= NO_SEATS;
    }

    private boolean isAvailableSeatsFor(String flightNumber, int numberOfBookedFlight, int newTravelers) {
        return getAvailableSeats(flightNumber, numberOfBookedFlight + newTravelers) >= NO_SEATS;
    }

    private int getAvailableSeats(String flightNumber, int numberOfBookedFlight) {
        return flightRepository.numberOfSeats(flightNumber) - numberOfBookedFlight;
    }
}
