package travelAgency.domain.booking;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.exceptions.FlightTicketNumberException;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.passenger.Passenger;

import java.time.LocalDate;

public record FlightTicket(@NotNull String ticketNumber,
                           @NotNull BookingInformation bookingInformation) {

    public FlightTicket(@NotNull String ticketNumber, @NotNull BookingInformation bookingInformation) {
        this.ticketNumber = ticketNumber;
        this.bookingInformation = bookingInformation;
        validate();
    }

    public void validate() {

        if (ticketNumber.isBlank()) throw new FlightTicketNumberException();
    }

    public boolean canMatchWith(String flightName, String passengerFirstName, LocalDate passengerBirthday) {
        return bookingInformation.canMatchWith(flightName, passengerFirstName, passengerBirthday);
    }

    public boolean canMatchWith(String searchFlightNumber) {
        return flightNumber().equals(searchFlightNumber);
    }

    public boolean isEqualTicketNumber(String ticketNumber) {
        return this.ticketNumber.equals(ticketNumber);
    }

    public Flight flight() {
        return bookingInformation.flight();
    }

    public String flightNumber() {
        return bookingInformation.flightNumber();
    }

    public String passenger_id() {
        return bookingInformation.passengerId();
    }

    public int travelers() {
        return bookingInformation.numberOfTickets();
    }

    public Passenger passenger() {
        return bookingInformation.passenger();
    }
}
