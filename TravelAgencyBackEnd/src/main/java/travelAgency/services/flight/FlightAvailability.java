package travelAgency.services.flight;

import travelAgency.domain.reservation.ReservationInformation;

public interface FlightAvailability {
    void canBooking(ReservationInformation reservationInformation);
}
