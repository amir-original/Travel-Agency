package travelAgency.services.bookingList;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.booking.BookingInformation;
import travelAgency.domain.booking.FlightTicket;
import travelAgency.domain.exceptions.NotFoundAnyBookingFlightException;
import travelAgency.repository.booking.BookingListRepository;

import java.time.LocalDate;
import java.util.List;

public class BookingListServiceImpl implements BookingListService {

    private final BookingListRepository bookings;
    private final TicketNumberGenerator ticketNumberGenerator;

    private final SearchTicketEngine searchEngine;


    public BookingListServiceImpl(BookingListRepository bookings,
                                  TicketNumberGenerator ticketNumberGenerator) {
        this.bookings = bookings;
        this.ticketNumberGenerator = ticketNumberGenerator;
        this.searchEngine = new SearchTicketEngine(getTickets());
    }


    @Override
    public FlightTicket book(BookingInformation bookingInformation) {
        final FlightTicket flightTicket = createTicket(bookingInformation);

        flightTicket.check();

        bookings.book(flightTicket);

        return flightTicket;
    }



    @NotNull
    private FlightTicket createTicket(BookingInformation bookingInformation) {
        final String ticketNumber = ticketNumberGenerator.generate();

        return new FlightTicket(ticketNumber, bookingInformation);
    }

    @Override
    public FlightTicket search(String flightNumber, String passengerFirstName, LocalDate passengerBirthday) {
       return searchEngine.search(flightNumber,passengerFirstName,passengerBirthday);
    }

    @Override
    public void cancel(FlightTicket flightTicket) {
        bookings.cancel(flightTicket);
    }

    @Override
    public int getBookedSeats(String flightNumber) {
        return bookings.tickets(flightNumber).stream().mapToInt(FlightTicket::travelers).sum();
    }

    @Override
    public FlightTicket ticket(String ticketNumber) {
        return bookings.ticket(ticketNumber).orElseThrow(NotFoundAnyBookingFlightException::new);
    }

    private List<FlightTicket> getTickets() {
        return bookings.tickets();
    }

}
