package travelAgency.domain.reservation;

import travelAgency.domain.exceptions.FullyBookedException;
import travelAgency.domain.exceptions.NotEnoughCapacityException;
import travelAgency.domain.reservation.ReservationInformation;
import travelAgency.domain.reservation.Reservation;

import java.util.List;

public class SeatAvailabilityChecker {

    private static final int NO_AVAILABLE_SEATS = -1;

    private final ReservationInformation reservationInformation;

    public SeatAvailabilityChecker(ReservationInformation reservationInformation) {
        this.reservationInformation = reservationInformation;
    }


}
