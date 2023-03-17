package travelAgency.domain;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public record FlightTicket(@NotNull String ticketNumber,
                           @NotNull FlightTicketInfo flightTicketInfo) {

    public void check() {
        flightTicketInfo.check();
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

    public boolean canMatchWith(String flightName, String passengerFirstName, LocalDate passengerBirthday) {
        return flightTicketInfo.canMatchWith(flightName,passengerFirstName,passengerBirthday);
    }

    public FlightPlan flightPlan() {
        return flightTicketInfo.flightPlan();
    }

    public Passenger passenger() {
        return flightTicketInfo.passenger();
    }
}
