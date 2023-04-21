package travelAgency.services.reservation;

import travelAgency.domain.reservation.Reservation;
import travelAgency.domain.exceptions.ReservationNotFoundException;

import java.time.LocalDate;
import java.util.List;

public record SearchTicketEngine(List<Reservation> tickets) {

    public Reservation search(String flightNumber, String passengerFirstName, LocalDate passengerBirthday) {
        return tickets.stream()
                .filter(reservation -> reservation.canMatchWith(flightNumber, passengerFirstName, passengerBirthday))
                .findFirst()
                .orElseThrow(ReservationNotFoundException::new);
    }

}
