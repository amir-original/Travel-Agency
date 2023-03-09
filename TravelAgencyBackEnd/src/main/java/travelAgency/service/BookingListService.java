package travelAgency.service;

import travelAgency.domain.BookingInformation;

import java.time.LocalDate;
import java.util.List;

public interface BookingListService {
    BookingInformation search(String flightName, String passengerFirstName, LocalDate PassengerBirthday);

    List<BookingInformation> getAllTickets();
}
