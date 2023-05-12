package travelAgency.services.reservation;

import travelAgency.domain.reservation.Reservation;
import travelAgency.domain.exceptions.ReservationNotFoundException;

import java.time.LocalDate;

public interface ReservationListService {

    void cancel(String ticketNumber) throws ReservationNotFoundException;
    Reservation search(String flightNumber, String passengerFirstName, LocalDate PassengerBirthday);
    Reservation search(String reservationNumber)  throws ReservationNotFoundException;;
    int getTotalBookedSeats(String flightNumber);
    int getAvailableSeats(String flightNumber);
}
