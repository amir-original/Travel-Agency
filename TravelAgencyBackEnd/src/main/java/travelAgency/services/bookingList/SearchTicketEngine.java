package travelAgency.services.bookingList;

import travelAgency.domain.booking.FlightTicket;
import travelAgency.domain.exceptions.NotFoundAnyBookingFlightException;

import java.time.LocalDate;
import java.util.List;

public record SearchTicketEngine(List<FlightTicket> tickets) {

    public FlightTicket search(String flightNumber, String passengerFirstName, LocalDate passengerBirthday) {
        return tickets.stream()
                .filter(booking -> booking.canMatchWith(flightNumber, passengerFirstName, passengerBirthday))
                .findFirst()
                .orElseThrow(NotFoundAnyBookingFlightException::new);
    }

}
