package travelAgency.services.bookingList;

import travelAgency.domain.booking.FlightTicket;
import travelAgency.domain.exceptions.NotFoundAnyBookingFlightException;
import travelAgency.repository.booking.BookingListRepository;

import java.time.LocalDate;
import java.util.List;

public class SearchTicketEngine implements SearchTicketService {
    private final BookingListRepository bookingListRepository;

    private List<FlightTicket> tickets;

    public SearchTicketEngine(BookingListRepository bookingListRepository) {
        this.bookingListRepository = bookingListRepository;
        getTickets();
    }

    private void getTickets() {
        tickets = bookingListRepository.tickets();
    }


    public FlightTicket search(String flightNumber, String passengerFirstName, LocalDate passengerBirthday) {
        return tickets.stream()
                .filter(booking -> booking.canMatchWith(flightNumber, passengerFirstName, passengerBirthday))
                .findFirst()
                .orElseThrow(NotFoundAnyBookingFlightException::new);
    }

}
