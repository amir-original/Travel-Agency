package travelAgency.repository;

import travelAgency.domain.BookingInformation;

import java.util.List;

public interface BookingListRepository {

    List<BookingInformation> getAllTickets();
}
