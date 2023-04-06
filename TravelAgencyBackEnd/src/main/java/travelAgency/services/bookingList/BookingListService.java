package travelAgency.services.bookingList;

import travelAgency.domain.booking.BookingInformation;
import travelAgency.domain.booking.FlightTicket;

import java.time.LocalDate;
import java.util.List;

public interface BookingListService {

    FlightTicket book(BookingInformation bookingInformation);
    void cancel(String ticketNumber);
    FlightTicket search(String flightNumber,
                        String passengerFirstName,
                        LocalDate PassengerBirthday);
    int getBookedSeats(String flightNumber);
    List<FlightTicket> getAllBookings();
    FlightTicket findBooking(String ticketNumber);


}
