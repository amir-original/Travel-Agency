package travelAgency.services.reservation;

import travelAgency.domain.reservation.Reservation;
import travelAgency.exceptions.ReservationNotFoundException;

import java.time.LocalDate;
import java.util.List;

public record SearchReservationEngine(List<Reservation> reservations) {

    public Reservation search(String flightNumber, String passengerFirstName, LocalDate passengerBirthday) {
        return reservations.stream()
                .filter(reservation -> reservation.canMatchWith(flightNumber, passengerFirstName, passengerBirthday))
                .findFirst()
                .orElseThrow(ReservationNotFoundException::new);
    }

}
