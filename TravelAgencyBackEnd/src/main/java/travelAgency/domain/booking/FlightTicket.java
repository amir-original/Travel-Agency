package travelAgency.domain.booking;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.exceptions.FlightTicketNumberException;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightPlan;
import travelAgency.domain.passenger.Passenger;

import java.time.LocalDate;
import java.util.Objects;

public final class FlightTicket {
    private final @NotNull String ticketNumber;
    private final @NotNull FlightTicketInfo flightTicketInfo;

    public FlightTicket(@NotNull String ticketNumber,
                        @NotNull FlightTicketInfo flightTicketInfo) {
        this.ticketNumber = ticketNumber;
        this.flightTicketInfo = flightTicketInfo;
    }

    public boolean canMatchWith(String flightName, String passengerFirstName, LocalDate passengerBirthday) {
        return flightTicketInfo.canMatchWith(flightName, passengerFirstName, passengerBirthday);
    }

    public void check() {
        if (ticketNumber.isBlank()) throw new FlightTicketNumberException();
    }

    public Flight flight() {
        return flightTicketInfo.flight();
    }

    public String flightNumber() {
        return flightTicketInfo.flightNumber();
    }

    public String passenger_id() {
        return flightTicketInfo.passenger_id();
    }

    public int numberOfTickets() {
        return flightTicketInfo.numberOfTickets();
    }

    public Passenger passenger() {
        return flightTicketInfo.passenger();
    }

    public boolean isEqualTicketNumber(String ticketNumber) {
        return this.ticketNumber.equals(ticketNumber);
    }

    public @NotNull String ticketNumber() {
        return ticketNumber;
    }

    public @NotNull FlightTicketInfo flightTicketInfo() {
        return flightTicketInfo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (FlightTicket) obj;
        return Objects.equals(this.ticketNumber, that.ticketNumber) &&
                Objects.equals(this.flightTicketInfo, that.flightTicketInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketNumber, flightTicketInfo);
    }

    @Override
    public String toString() {
        return "FlightTicket[" +
                "ticketNumber=" + ticketNumber + ", " +
                "flightTicketInfo=" + flightTicketInfo + ']';
    }

}
