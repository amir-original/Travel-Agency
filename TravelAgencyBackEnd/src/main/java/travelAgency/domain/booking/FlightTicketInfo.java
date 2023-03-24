package travelAgency.domain.booking;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightPlan;
import travelAgency.domain.passenger.Passenger;

import java.time.LocalDate;

public record FlightTicketInfo(@NotNull Flight flight,
                           @NotNull BookingInformation bookingInformation) {

    public boolean canMatchWith(String flightNumber, String passengerFirstName,
                                LocalDate PassengerBirthday) {

        return flight.flightNumber().equals(flightNumber) &&
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
