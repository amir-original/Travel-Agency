package travelAgency.domain;

import travelAgency.helper.UniqueIdGenerator;

public class TicketNumberGenerator {

    private static final int MAX_LENGTH = 8;

    public String generate() {
        return UniqueIdGenerator.generate(MAX_LENGTH);
    }
}
