package travelAgency.services.booking;

import travelAgency.domain.FlightTicket;
import travelAgency.repository.BookingFlightRepository;
import travelAgency.services.flights.FindFlightsService;

public class BookingFlight implements BookingFlightService {

    private final BookingFlightRepository bookingRepository;
    private final ValidateTicket validateTicket;

    public BookingFlight(BookingFlightRepository bookingRepository, FindFlightsService flightService) {
        this.bookingRepository = bookingRepository;
        validateTicket = new ValidateTicket(flightService);
    }

    public void book(FlightTicket flightTicket) {
        validateTicket.validate(flightTicket);

        // booking
        bookingRepository.book(flightTicket);
    }

}
