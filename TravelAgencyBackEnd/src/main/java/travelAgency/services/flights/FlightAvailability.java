package travelAgency.services.flights;

import travelAgency.domain.booking.BookingInformation;

public interface FlightAvailability {
    void checkFlight(BookingInformation bookingInformation);
}
