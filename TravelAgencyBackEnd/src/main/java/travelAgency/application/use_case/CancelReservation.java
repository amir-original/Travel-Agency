package travelAgency.application.use_case;

import travelAgency.model.reservation.ReservationRepository;
import travelAgency.model.reservation.Reservation;
import travelAgency.infrastructure.persistence.jdbc_mysql.reservation.CouldNotFoundReservation;

// application service
public final class CancelReservation {
    
    private final ReservationRepository reservations;

    public CancelReservation(ReservationRepository reservations) {
        this.reservations = reservations;
    }
    
    public void cancelReservation(String reservationNumber){
        final Reservation reservation = reservations.findReservation(reservationNumber)
                .orElseThrow(CouldNotFoundReservation::new);

        reservation.ensureCanCancel();

        reservations.cancel(reservation);
    }

}
