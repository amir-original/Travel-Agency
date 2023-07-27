package travelAgency.application.use_case;

import travelAgency.model.reservation.ReservationNumber;

public interface ReservationNumberGenerator {
    ReservationNumber generateReservationNumber();
}
