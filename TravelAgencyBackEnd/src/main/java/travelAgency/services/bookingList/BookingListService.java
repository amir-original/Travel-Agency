package travelAgency.services.bookingList;

import travelAgency.domain.booking.FlightTicket;

import java.time.LocalDate;
import java.util.List;

public interface BookingListService {

    void cancel(String ticketNumber);
    FlightTicket search(String flightNumber,
                        String passengerFirstName,
                        LocalDate PassengerBirthday);
    int getBookedSeats(String flightNumber);
    int availableSeats(String flightNumber);
    List<FlightTicket> getAllBookings();
}
