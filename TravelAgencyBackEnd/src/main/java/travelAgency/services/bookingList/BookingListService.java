package travelAgency.services.bookingList;

import travelAgency.domain.booking.FlightTicket;

import java.time.LocalDate;

public interface BookingListService {

    FlightTicket search(String flightNumber,
                                     String passengerFirstName,
                                     LocalDate PassengerBirthday);

    void cancel(String ticketNumber);
}
