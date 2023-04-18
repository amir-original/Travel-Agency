package travelAgency.services.bookingList;

import travelAgency.domain.booking.Reservation;
import travelAgency.domain.exceptions.ReservationNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface BookingListService {

    void cancel(String ticketNumber) throws ReservationNotFoundException;
    Reservation search(String flightNumber,
                       String passengerFirstName,
                       LocalDate PassengerBirthday);
    int getBookedSeats(String flightNumber);
    List<Reservation> getAllReservations();
}
