package travelAgency.services.bookingList;

import travelAgency.domain.FlightTicket;
import travelAgency.repository.BookingListRepository;

import java.util.List;

public class BookingListServiceImpl implements BookingListService {

    private final BookingListRepository bookingListRepository;

    public BookingListServiceImpl(BookingListRepository bookingListRepository) {
        this.bookingListRepository = bookingListRepository;
    }

    @Override
    public List<FlightTicket> getAllTickets() {
        return bookingListRepository.getAllTickets();
    }

}
