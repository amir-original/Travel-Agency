package travelAgency.fake;

import travelAgency.services.bookingList.TicketGenerator;

public class FakeTicketGenerator implements TicketGenerator {

    @Override
    public String generateTicketNumber() {
        return "78456587";
    }
}
