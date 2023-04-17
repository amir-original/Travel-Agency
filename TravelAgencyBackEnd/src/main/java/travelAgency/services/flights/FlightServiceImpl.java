package travelAgency.services.flights;

import travelAgency.domain.exceptions.FlightNotFoundException;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightPlan;
import travelAgency.repository.booking.BookingListRepository;
import travelAgency.repository.flight.FlightRepository;

import java.util.List;

public class FlightServiceImpl implements FlightService {

    private final FlightRepository flights;
    private final BookingListRepository bookings;

    public FlightServiceImpl(FlightRepository flights,BookingListRepository bookingListRepository) {
        this.flights = flights;
        bookings = bookingListRepository;
    }

    @Override
    public List<Flight> searchFlights(FlightPlan searchFlightPlan) {
        return searchFlightPlan.search(flights.flights());
    }

    @Override
    public Flight findFlight(String flightNumber) {
        return flights.findFlight(flightNumber)
                .orElseThrow(FlightNotFoundException::new);
    }

    @Override
    public int availableSeats(String flightNumber) {
        return findFlight(flightNumber).getAvailableSeats(bookings.getAllBookings());
    }

}
