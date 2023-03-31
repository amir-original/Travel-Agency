package travelAgency.domain.booking;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.exceptions.NumberOfTravelersException;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightPlan;
import travelAgency.domain.passenger.Passenger;

import java.time.LocalDate;

public record BookingInformation(@NotNull Flight flight, @NotNull Passenger passenger, int numberOfTickets) {

    public void check() {
        flight.check();
        checkTicketNumber();
        passenger.check();
    }

    private void checkTicketNumber() {
        if (numberOfTickets() <= 0) throw new NumberOfTravelersException();
    }

    public boolean canMatchWith(String flightNumber, String passengerFirstName,
                                LocalDate passengerBirthday) {

        return flight.flightNumber().equals(flightNumber) &&
                passenger.canMatchWith(passengerFirstName,passengerBirthday);
    }



    public FlightPlan flightPlan() {
        return flight.plan();
    }

    public String flightNumber() {
        return flight.flightNumber();
    }

    public String passengerId() {
        return passenger.id();
    }
}
