package travelAgency.services.bookingList;

import travelAgency.domain.booking.FlightTicket;
import travelAgency.domain.booking.FlightTicketInfo;
import travelAgency.domain.booking.TicketNumberGenerator;
import travelAgency.repository.booking.BookingListRepository;
import travelAgency.services.booking.BookingFlightService;
import travelAgency.services.booking.BookingFlightServiceImpl;

import java.time.LocalDate;
import java.util.List;

public class BookingListServiceImpl implements BookingListService {

    private final BookingListRepository bookingListRepository;
    private final BookingFlightService bookingFlightService;

    private final SearchTicketEngine searchEngine;


    public BookingListServiceImpl(BookingListRepository bookingListRepository,
                                  TicketNumberGenerator ticketNumberGenerator) {
        this.bookingListRepository = bookingListRepository;
        this.bookingFlightService = new BookingFlightServiceImpl(bookingListRepository,ticketNumberGenerator);
        this.searchEngine = new SearchTicketEngine(getTickets());
    }


    @Override
    public FlightTicket book(FlightTicketInfo flightTicketInfo) {
        return bookingFlightService.book(flightTicketInfo);
    }

    @Override
    public FlightTicket search(String flightNumber, String passengerFirstName, LocalDate passengerBirthday) {
       return searchEngine.search(flightNumber,passengerFirstName,passengerBirthday);
    }

    @Override
    public void cancel(FlightTicket flightTicket) {
        bookingListRepository.remove(flightTicket);
    }

    @Override
    public boolean isExistFlightTicket(String ticketNumber) {
        return bookingListRepository.ticket(ticketNumber).isPresent();
    }

    @Override
    public int numberOfBookedFlight(String flightNumber) {
        return bookingListRepository.numberOfBookedFlight(flightNumber);
    }

    @Override
    public int numberOfSeatsAvailable(String flightNumber) {
        return 0;
    }

    private List<FlightTicket> getTickets() {
        return bookingListRepository.tickets();
    }

}
