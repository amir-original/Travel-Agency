package travelAgency.services.bookingList;

import travelAgency.domain.booking.FlightTicket;

import java.time.LocalDate;
import java.util.List;

public interface BookingListService {

    FlightTicket search(String flightNumber,
                                     String passengerFirstName,
                                     LocalDate PassengerBirthday);

    void cancel(FlightTicket flightTicket);

    boolean isExistFlightTicket(FlightTicket ticket);

}
