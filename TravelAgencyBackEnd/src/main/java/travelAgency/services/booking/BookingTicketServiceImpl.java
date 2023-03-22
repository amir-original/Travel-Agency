package travelAgency.services.booking;

import travelAgency.domain.booking.FlightTicket;
import travelAgency.domain.booking.FlightTicketInfo;
import travelAgency.domain.booking.TicketNumberGenerator;
import travelAgency.repository.booking.BookingFlightRepository;

public class BookingTicketServiceImpl implements BookingTicketService {
    private final BookingFlightRepository bookingFlightRepository;
    private final TicketNumberGenerator ticketNumberGenerator = new TicketNumberGenerator();

    public BookingTicketServiceImpl(BookingFlightRepository bookingFlightRepository) {
        this.bookingFlightRepository = bookingFlightRepository;
    }

    @Override
    public FlightTicket book(FlightTicketInfo flightTicketInfo) {
        final String ticketNumber = ticketNumberGenerator.generate();

        final FlightTicket flightTicket = new FlightTicket(ticketNumber, flightTicketInfo);

        bookingFlightRepository.book(flightTicket);

        return flightTicket;
    }
}
