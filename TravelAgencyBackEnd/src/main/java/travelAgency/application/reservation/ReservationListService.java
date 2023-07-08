package travelAgency.application.reservation;

import travelAgency.application.dto.FlightDto;
import travelAgency.exceptions.FlightNotFoundException;
import travelAgency.model.reservation.Reservation;
import travelAgency.exceptions.ReservationNotFoundException;

import java.time.LocalDate;

public interface ReservationListService {
    Reservation search(String flightNumber, String passengerFirstName, LocalDate PassengerBirthday);
    Reservation search(String reservationNumber)  throws ReservationNotFoundException;
    FlightDto findFlight(String flightNumber) throws FlightNotFoundException;
    int getTotalBookedSeats(String flightNumber);
    int availableSeats(String flightNumber);
}
