package travelAgency.services.bookingList;

import travelAgency.domain.booking.FlightTicket;
import travelAgency.repository.booking.BookingListRepositoryImpl;
import travelAgency.repository.db.mysq.MySQLDbConnection;

import java.time.LocalDate;

public class BookingListServiceImpl implements BookingListService {

    private final SearchTicketService searchEngine;

    public BookingListServiceImpl() {
        final BookingListRepositoryImpl bookingListRepository = new BookingListRepositoryImpl(new MySQLDbConnection());
        this.searchEngine = new SearchTicketEngine(bookingListRepository);
    }

    @Override
    public FlightTicket search(String flightNumber, String passengerFirstName, LocalDate PassengerBirthday) {
        return searchEngine.search(flightNumber,passengerFirstName,PassengerBirthday);
    }

    @Override
    public void cancel(String ticketNumber) {

    }
}
