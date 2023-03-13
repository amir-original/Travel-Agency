package travelAgency.fakeData;

import travelAgency.domain.FlightTicket;
import travelAgency.repository.BookingListRepository;

import java.util.List;

public class FakeBookingList implements BookingListRepository {
    private final FakeTicketsData bookingListFake = new FakeTicketsData();

    @Override
    public List<FlightTicket> getAllTickets() {
        return bookingListFake.getFakeBookingTickets();
    }
}
