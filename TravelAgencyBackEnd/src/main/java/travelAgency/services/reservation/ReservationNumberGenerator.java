package travelAgency.services.reservation;

import travelAgency.domain.reservation.ReservationNumber;

public interface ReservationNumberGenerator {
    ReservationNumber generateReservationNumber();
}
