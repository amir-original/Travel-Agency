package travelAgency.domain.booking;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.exceptions.FlightTicketNumberNotNullException;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightPlan;
import travelAgency.domain.passenger.Passenger;

import java.time.LocalDate;

public record FlightTicket(@NotNull String ticketNumber,
                           @NotNull FlightTicketInfo flightTicketInfo) {

    public boolean canMatchWith(String flightName, String passengerFirstName, LocalDate passengerBirthday) {
        return flightTicketInfo.canMatchWith(flightName,passengerFirstName,passengerBirthday);
    }

    public void check(){
        if (ticketNumber.isBlank()) throw new FlightTicketNumberNotNullException();
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

    public FlightPlan flightPlan() {
        return flightTicketInfo.flightPlan();
    }

    public Passenger passenger() {
        return flightTicketInfo.passenger();
    }
}
