package travelAgency.services.bookingList;

import travelAgency.domain.booking.FlightTicket;
import travelAgency.repository.booking.BookingListRepository;

import java.util.List;
import java.util.Optional;

public class BookingListServiceImpl implements BookingListService {

    private final BookingListRepository bookingListRepository;

    public BookingListServiceImpl(BookingListRepository bookingListRepository) {
        this.bookingListRepository = bookingListRepository;
    }

    @Override
    public List<FlightTicket> getAllTickets() {
        return bookingListRepository.tickets();
    }

    @Override
    public Optional<FlightTicket> getTicket(String ticketNumber) {
        return bookingListRepository.ticket(ticketNumber);
    }

}
