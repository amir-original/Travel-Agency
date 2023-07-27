package travelAgency.application.use_case;

import travelAgency.model.reservation.ReservationRepository;
import travelAgency.model.reservation.Reservation;
import travelAgency.exceptions.CouldNotCancelReservation;
import travelAgency.exceptions.CouldNotFoundReservation;

// application service
public final class CancelReservation {
    
    private final ReservationRepository reservations;

    public CancelReservation(ReservationRepository reservations) {
        this.reservations = reservations;
    }
    
    public void cancelReservation(String reservationNumber){
        final Reservation reservation = reservations.findReservation(reservationNumber)
                .orElseThrow(CouldNotFoundReservation::new);

        ensureCanCancel(reservation);

        reservations.cancel(reservation);
    }

    private void ensureCanCancel(Reservation reservation) {
        if (reservation.flight().isDeparted()){
            throw CouldNotCancelReservation.becauseFlightIsDeparted();
        }
    }
}
