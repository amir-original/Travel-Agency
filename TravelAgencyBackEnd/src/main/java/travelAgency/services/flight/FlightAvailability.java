package travelAgency.services.flight;

import travelAgency.domain.reservation.BookingInformation;

public interface FlightAvailability {
    void checkFlightPreBooking(BookingInformation bookingInformation);
}
