package travelAgency.services.bookingList;

import travelAgency.domain.booking.BookingInformation;
import travelAgency.domain.booking.FlightTicket;

import java.time.LocalDate;

public interface BookingListService {

    FlightTicket book(BookingInformation bookingInformation);
    FlightTicket search(String flightNumber,
                        String passengerFirstName,
                        LocalDate PassengerBirthday);
    void cancel(String ticketNumber);
    int getBookedSeats(String flightNumber);
    FlightTicket findBooking(String ticketNumber);

}
