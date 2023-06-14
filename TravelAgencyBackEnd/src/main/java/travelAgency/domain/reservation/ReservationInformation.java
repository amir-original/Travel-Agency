package travelAgency.domain.reservation;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.vo.PassengerId;
import travelAgency.exceptions.InvalidNumberOfTicketsException;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.passenger.Passenger;

import java.time.LocalDate;

import static java.util.stream.Stream.of;

public record ReservationInformation(@NotNull Flight flight,
                                     @NotNull Passenger passenger,
                                     int numberOfTickets) {

    public ReservationInformation(@NotNull Flight flight,
                                  @NotNull Passenger passenger,
                                  int numberOfTickets) {
        this.flight = flight;
        this.passenger = passenger;
        this.numberOfTickets = numberOfTickets;
        validate();
    }

    public void validate() {
        if (hasNoTickets())
            throw new InvalidNumberOfTicketsException();
    }

    private boolean hasNoTickets() {
        return numberOfTickets() <= 0;
    }

    public Reservation getReservation(String ticketNumber) {
        return new Reservation(ticketNumber, this);
    }

    public boolean canMatchWith(String flightNumber,
                                String passengerFirstName,
                                LocalDate passengerBirthday) {

        return flight.hasSameFlightNumber(flightNumber) &&
                passenger.canMatchWith(passengerFirstName, passengerBirthday);
    }

    public String flightNumber() {
        return flight.flightNumber();
    }

    public PassengerId passengerId() {
        return passenger.passengerId();
    }
}
