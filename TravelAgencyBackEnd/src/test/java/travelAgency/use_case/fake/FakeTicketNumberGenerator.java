package travelAgency.use_case.fake;

import travelAgency.services.bookingList.TicketNumberGenerator;

public class FakeTicketNumberGenerator implements TicketNumberGenerator {

    @Override
    public String generateTicketNumber() {
        return "78456587";
    }
}
