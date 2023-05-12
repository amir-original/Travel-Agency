package travelAgency.dao.database.reservation;

import travelAgency.domain.reservation.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationListRepository {
    void book(Reservation reservation);
    void cancel(String ticketNumber);
    Optional<Reservation> findReservation(String reservationNumber);
    Optional<Reservation> findReservationByFlightNumber(String flightNumber);
    List<Reservation> getReservations();
    void truncate();
}
