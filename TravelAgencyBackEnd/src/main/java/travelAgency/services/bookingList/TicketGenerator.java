package travelAgency.services.bookingList;

import travelAgency.domain.booking.BookingInformation;
import travelAgency.domain.booking.FlightTicket;
import travelAgency.helper.UniqueIdGenerator;

public class TicketGenerator {

    private static final int MAX_LENGTH = 8;

    public String generate() {
        return UniqueIdGenerator.generate(MAX_LENGTH);
    }

}
