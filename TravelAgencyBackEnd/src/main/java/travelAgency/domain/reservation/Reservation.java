package travelAgency.domain.reservation;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.exceptions.InvalidTicketNumberException;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.passenger.Passenger;

import java.time.LocalDate;

import static java.lang.String.format;
import static travelAgency.helper.PriceFormat.formatPriceWithSymbol;

public record Reservation(@NotNull String ticketNumber,
                          @NotNull ReservationInformation reservationInformation) {

    public Reservation {
        validate(ticketNumber);
    }

    private void validate(String ticketNumber) {
        if (ticketNumber.isBlank()) throw new InvalidTicketNumberException();
    }

    public boolean canMatchWith(String flightNumber, String passengerFirstName, LocalDate passengerBirthday) {
        return reservationInformation.canMatchWith(flightNumber, passengerFirstName, passengerBirthday);
    }

    public boolean canMatchWith(String searchFlightNumber) {
        return flight().hasSameFlightNumber(searchFlightNumber);
    }

    public boolean hasSameTicketNumber(String ticketNumber) {
        return this.ticketNumber.equals(ticketNumber);
    }

    public Flight flight() {
        return reservationInformation.flight();
    }

    public String flightNumber() {
        return reservationInformation.flightNumber();
    }

    public String passengerId() {
        return reservationInformation.passengerId();
    }

    public int travelers() {
        return reservationInformation.numberOfTickets();
    }

    public Passenger passenger() {
        return reservationInformation.passenger();
    }

    public String buildTicket() {
        return format(""" 
                        Passenger Name: %s               Flight Number: %s           Ticket Number: %s  
                                From : %s  📍 ---------------------------------------------- ✈  To: %s
                        Departure: %s                    Arrival: %s                 Price: %s     
                        """,
                passenger().fullName(),
                flightNumber(),
                ticketNumber,
                flight().from(),
                flight().to(),
                flight().departure(),
                flight().arrival(),
                formatPriceWithSymbol(flight().price()));
    }
}
