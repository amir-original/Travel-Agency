package travelAgency.services.bookingList;

import travelAgency.domain.booking.FlightTicket;
import travelAgency.domain.booking.FlightTicketInfo;
import travelAgency.services.booking.BookingFlightService;

import java.time.LocalDate;

public interface BookingListService {

    FlightTicket book(FlightTicketInfo flightTicketInfo);

    FlightTicket search(String flightNumber,
                        String passengerFirstName,
                        LocalDate PassengerBirthday);

    void cancel(FlightTicket flightTicket);

    boolean isExistFlightTicket(String ticketNumber);

    int numberOfBookedFlight(String flightNumber);
    int numberOfSeatsAvailable(String flightNumber);
}
