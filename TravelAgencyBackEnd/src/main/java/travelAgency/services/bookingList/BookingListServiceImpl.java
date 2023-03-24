package travelAgency.services.bookingList;

import travelAgency.domain.booking.FlightTicket;
import travelAgency.repository.booking.BookingListRepository;
import travelAgency.repository.booking.BookingListRepositoryImpl;
import travelAgency.repository.db.mysq.MySQLDbConnection;

import java.time.LocalDate;

public class BookingListServiceImpl implements BookingListService {

    private final SearchTicketEngine searchEngine;
    private final BookingListRepository bookingListRepository;

    public BookingListServiceImpl(BookingListRepository bookingListRepository) {
        this.bookingListRepository = bookingListRepository;
        this.searchEngine = new SearchTicketEngine(bookingListRepository);
    }

    @Override
    public FlightTicket search(String flightNumber, String passengerFirstName, LocalDate PassengerBirthday) {
        return searchEngine.search(flightNumber,passengerFirstName,PassengerBirthday);
    }

    @Override
    public void cancel(String ticketNumber) {
        // cancel booking
    }
}
