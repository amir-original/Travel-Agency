package travelAgency.use_case.fake;

import travelAgency.services.reservation.TicketNumberGenerator;

public class FakeTicketNumberGenerator implements TicketNumberGenerator {

    @Override
    public String getTicketNumberFormat() {
        return "AA-7845-65874";
    }
}
