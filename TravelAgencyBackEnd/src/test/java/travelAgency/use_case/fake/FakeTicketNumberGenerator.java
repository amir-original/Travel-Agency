package travelAgency.use_case.fake;

import travelAgency.services.bookingList.TicketNumberGenerator;

public class FakeTicketNumberGenerator implements TicketNumberGenerator {

    @Override
    public String getTicketNumberFormat() {
        return "AA-7845-65874";
    }
}
