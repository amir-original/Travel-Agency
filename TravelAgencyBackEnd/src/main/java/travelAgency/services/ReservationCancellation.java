package travelAgency.services;

import travelAgency.dao.database.reservation.ReservationListRepository;
import travelAgency.domain.reservation.Reservation;
import travelAgency.exceptions.CouldNotCancelReservation;
import travelAgency.exceptions.CouldNotFoundReservation;

// application service
public final class ReservationCancellation {
    
    private final ReservationListRepository reservations;

    public ReservationCancellation(ReservationListRepository reservations) {
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
