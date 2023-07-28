package travelAgency.model.reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    void book(Reservation reservation);
    void cancel(String reservationNumber);
    void cancel(Reservation reservation);
    Optional<Reservation> findReservation(String reservationNumber);

    List<Reservation> getReservations();
    void truncate();
}
