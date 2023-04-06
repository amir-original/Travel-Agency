package travelAgency.services.bookingList;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.booking.BookingInformation;
import travelAgency.domain.booking.FlightTicket;

public interface TicketGenerator {

    String generateTicketNumber();

    @NotNull
    default FlightTicket getFlightTicket(BookingInformation bi) {
        final FlightTicket flightTicket = createTicket(bi);
        flightTicket.check();
        return flightTicket;
    }

    private FlightTicket createTicket(BookingInformation bookingInformation) {
        return new FlightTicket(generateTicketNumber(), bookingInformation);
    }

}
