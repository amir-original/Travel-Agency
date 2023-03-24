package travelAgency.services.booking;

import travelAgency.domain.booking.FlightTicket;
import travelAgency.domain.booking.FlightTicketInfo;
import travelAgency.domain.booking.TicketNumberGenerator;
import travelAgency.repository.booking.BookingFlightRepository;

public class BookingTicketServiceImpl implements BookingTicketService {
    private final BookingFlightRepository bookingFlightRepository;
    private final TicketNumberGenerator ticketNumberGenerator;

    public BookingTicketServiceImpl(BookingFlightRepository bookingFlightRepository,
                                    TicketNumberGenerator ticketNumberGenerator) {
        this.bookingFlightRepository = bookingFlightRepository;
        this.ticketNumberGenerator = ticketNumberGenerator;
    }

    @Override
    public FlightTicket book(FlightTicketInfo flightTicketInfo) {
        final String ticketNumber = ticketNumberGenerator.generate();

        final FlightTicket flightTicket = new FlightTicket(ticketNumber, flightTicketInfo);
        flightTicket.check();

        bookingFlightRepository.book(flightTicket);

        return flightTicket;
    }
}
