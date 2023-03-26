package travelAgency.services.bookingList;

import travelAgency.domain.booking.FlightTicket;
import travelAgency.domain.exceptions.NotFoundAnyBookingFlightException;
import travelAgency.repository.booking.BookingListRepository;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class BookingListServiceImpl implements BookingListService {

    private final SearchTicketEngine searchEngine;
    private final BookingListRepository bookingListRepository;


    public BookingListServiceImpl(BookingListRepository bookingListRepository) {
        this.bookingListRepository = bookingListRepository;
        this.searchEngine = new SearchTicketEngine(getTickets());
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
    public boolean isExistFlightTicket(FlightTicket ticket) {
        return bookingListRepository.ticket(ticket.ticketNumber()).isPresent();
    }

    private List<FlightTicket> getTickets() {
        return bookingListRepository.tickets();
    }

}
