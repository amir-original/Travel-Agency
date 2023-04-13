package travelAgency.services.bookingList;

import travelAgency.domain.booking.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface BookingListService {

    void cancel(String ticketNumber);
    Reservation search(String flightNumber,
                       String passengerFirstName,
                       LocalDate PassengerBirthday);
    int getBookedSeats(String flightNumber);
    int availableSeats(String flightNumber);
    List<Reservation> getAllReservations();
}
