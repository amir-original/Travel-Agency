package travelAgency.services.booking;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.booking.FlightTicket;
import travelAgency.domain.booking.FlightTicketInfo;
import travelAgency.domain.booking.TicketNumberGenerator;
import travelAgency.repository.booking.BookingListRepository;

public class BookingFlightServiceImpl implements BookingFlightService {

    private final BookingListRepository bookingListRepository;
    private final TicketNumberGenerator ticketNumberGenerator;

    public BookingFlightServiceImpl(BookingListRepository bookingListRepository,
                                    TicketNumberGenerator ticketNumberGenerator) {
        this.bookingListRepository = bookingListRepository;
        this.ticketNumberGenerator = ticketNumberGenerator;
    }

    @Override
    public FlightTicket book(FlightTicketInfo flightTicketInfo) {
        final FlightTicket flightTicket = createTicket(flightTicketInfo);

        flightTicket.check();

        bookingListRepository.book(flightTicket);

        return flightTicket;
    }

    @NotNull
    private FlightTicket createTicket(FlightTicketInfo flightTicketInfo) {
        final String ticketNumber = ticketNumberGenerator.generate();

        return new FlightTicket(ticketNumber, flightTicketInfo);
    }
}
