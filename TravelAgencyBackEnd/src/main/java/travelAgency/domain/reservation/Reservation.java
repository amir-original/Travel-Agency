package travelAgency.domain.reservation;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.exceptions.FlightTicketNumberException;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.passenger.Passenger;

import java.time.LocalDate;
import java.util.List;

import static java.lang.String.format;

public record Reservation(@NotNull String ticketNumber,
                          @NotNull BookingInformation bookingInformation) {

    public Reservation(@NotNull String ticketNumber, @NotNull BookingInformation bookingInformation) {
        this.ticketNumber = ticketNumber;
        this.bookingInformation = bookingInformation;
        validate();
    }

    private void validate() {
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

    public int getBookedSeats(List<Reservation> reservations) {
        return bookingInformation.getBookedSeats(reservations);
    }

    public Flight flight() {
        return bookingInformation.flight();
    }

    public String flightNumber() {
        return bookingInformation.flightNumber();
    }

    public String passengerId() {
        return bookingInformation.passengerId();
    }

    public int travelers() {
        return bookingInformation.numberOfTickets();
    }

    public Passenger passenger() {
        return bookingInformation.passenger();
    }

    public String getTicketInfo() {
        return format(""" 
                        Passenger Name: %s               Flight Number: %s           Ticket Number: %s  
                                From : %s  📍 ------------------------------------------- ✈  To: %s
                        Departure: %s                    Arrival: %s                 Price: %s     
                        """,
                passenger().fullName(),
                flightNumber(),
                ticketNumber,
                flight().from(),
                flight().to(),
                flight().departure(),
                flight().arrival(),
                flight().price());
    }
}
