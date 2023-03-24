package travelAgency.services.bookingList;

import travelAgency.domain.booking.FlightTicket;

import java.time.LocalDate;

public interface SearchTicketService {

    FlightTicket search(String flightNumber,
                        String passengerFirstName,
                        LocalDate passengerBirthday);
}
