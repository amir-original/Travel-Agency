package travelAgency.domain;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public record FlightTicketInfo(@NotNull Flight flight,
                           @NotNull BookingInformation bookingInformation) {

    public boolean canMatchWith(String flightName, String passengerFirstName,
                                LocalDate PassengerBirthday) {

        return flight.flightNumber().equals(flightName) &&
                passenger().fName().equals(passengerFirstName) &&
                passenger().birthday().equals(PassengerBirthday);
    }

    public void check() {
        flight.check();
        bookingInformation.check();
    }

    public Passenger passenger() {
        return bookingInformation.passenger();
    }

    public FlightPlan flightPlan() {
        return flight.plan();
    }

    public String flightNumber() {
        return flight.flightNumber();
    }

    public int numberOfTickets() {
        return bookingInformation.numberOfTickets();
    }

    public String passenger_id() {
        return passenger().id();
    }

}
