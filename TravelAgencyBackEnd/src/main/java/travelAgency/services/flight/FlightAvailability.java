package travelAgency.services.flight;

import travelAgency.domain.reservation.ReservationInformation;

public interface FlightAvailability {
    void checkFlightPreBooking(ReservationInformation reservationInformation);
}
