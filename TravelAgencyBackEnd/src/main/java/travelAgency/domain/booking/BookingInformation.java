package travelAgency.domain.booking;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.exceptions.NumberOfTravelersException;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightPlan;
import travelAgency.domain.passenger.Passenger;

import java.time.LocalDate;
import java.util.List;

public record BookingInformation(@NotNull Flight flight,
                                 @NotNull Passenger passenger,
                                 int numberOfTickets) {

    public BookingInformation(@NotNull Flight flight, @NotNull Passenger passenger, int numberOfTickets) {
        this.flight = flight;
        this.passenger = passenger;
        this.numberOfTickets = numberOfTickets;
        validate();
    }

    public void validate() {
        if (this.numberOfTickets() <= 0) throw new NumberOfTravelersException();
    }

    public void checkExistenceFlight(List<Flight> flights) {
        flight.checkExistenceFlight(flights);
    }

    public Reservation getReservation(String ticketNumber) {
        return new Reservation(ticketNumber, this);
    }

    public boolean canMatchWith(String flightNumber, String passengerFirstName,
                                LocalDate passengerBirthday) {

        return flight.flightNumber().equals(flightNumber) &&
                passenger.canMatchWith(passengerFirstName, passengerBirthday);
    }


    public String flightNumber() {
        return flight.flightNumber();
    }

    public String passengerId() {
        return passenger.id();
    }
}
