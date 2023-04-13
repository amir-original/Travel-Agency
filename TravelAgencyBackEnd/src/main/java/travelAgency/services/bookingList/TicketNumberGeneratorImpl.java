package travelAgency.services.bookingList;

import travelAgency.helper.UniqueIdGenerator;

public class TicketNumberGeneratorImpl implements TicketNumberGenerator {

    private static final int MAX_LENGTH = 8;
    @Override
    public String generateTicketNumber() {
        return UniqueIdGenerator.generate(MAX_LENGTH);
    }
}
