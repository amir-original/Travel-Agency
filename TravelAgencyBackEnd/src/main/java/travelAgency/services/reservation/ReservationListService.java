package travelAgency.services.reservation;

import travelAgency.exceptions.FlightNotFoundException;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.reservation.Reservation;
import travelAgency.exceptions.ReservationNotFoundException;

import java.time.LocalDate;

public interface ReservationListService {

    void cancel(String reservationNumber) throws ReservationNotFoundException;
    Reservation search(String flightNumber, String passengerFirstName, LocalDate PassengerBirthday);
    Reservation search(String reservationNumber)  throws ReservationNotFoundException;
    Flight findFlight(String flightNumber) throws FlightNotFoundException;
    int getTotalBookedSeats(String flightNumber);
    int getAvailableSeats(String flightNumber);
}
