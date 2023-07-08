package travelAgency.application.reservation;

import travelAgency.model.reservation.ReservationRepository;
import travelAgency.model.reservation.Reservation;
import travelAgency.exceptions.CouldNotCancelReservation;
import travelAgency.exceptions.CouldNotFoundReservation;

// application service
public final class ReservationCancellation {
    
    private final ReservationRepository reservations;

    public ReservationCancellation(ReservationRepository reservations) {
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
