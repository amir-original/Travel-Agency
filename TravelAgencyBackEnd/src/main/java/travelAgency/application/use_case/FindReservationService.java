package travelAgency.application.use_case;

import travelAgency.application.dto.FlightDto;
import travelAgency.exceptions.FlightNotFoundException;
import travelAgency.model.reservation.Reservation;
import travelAgency.exceptions.ReservationNotFoundException;

import java.time.LocalDate;

public interface FindReservationService {
    Reservation search(String flightNumber, String passengerFirstName, LocalDate PassengerBirthday);
    Reservation search(String reservationNumber);
    FlightDto findFlight(String flightNumber);
    int totalBookedSeats(String flightNumber);
    int availableSeats(String flightNumber);
}
