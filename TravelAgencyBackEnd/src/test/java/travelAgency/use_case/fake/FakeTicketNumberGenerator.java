package travelAgency.use_case.fake;

import travelAgency.services.reservation.TicketNumberGenerator;

public class FakeTicketNumberGenerator implements TicketNumberGenerator {

    @Override
    public String generateTicketNumber() {
        return "AA-7845-65874";
    }
}
