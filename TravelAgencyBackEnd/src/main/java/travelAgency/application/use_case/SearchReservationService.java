package travelAgency.application.use_case;

import travelAgency.application.dto.FlightDto;
import travelAgency.application.dto.ReservationResponse;
import travelAgency.model.reservation.Reservation;

import java.time.LocalDate;

public interface SearchReservationService {
    ReservationResponse search(String flightNumber, String passengerFirstName, LocalDate PassengerBirthday);
    ReservationResponse search(String reservationNumber);
    FlightDto findFlight(String flightNumber);
    int totalBookedSeats(String flightNumber);
    int availableSeats(String flightNumber);
}
