package travelAgency.domain.booking;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.exceptions.NumberOfTravelersException;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.passenger.Passenger;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Stream.of;

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
        flight.validateSchedule();
    }

    public Reservation getReservation(String ticketNumber) {
        return new Reservation(ticketNumber, this);
    }

    public boolean canMatchWith(String flightNumber, String passengerFirstName,
                                LocalDate passengerBirthday) {

        return flight.flightNumber().equals(flightNumber) &&
                passenger.canMatchWith(passengerFirstName, passengerBirthday);
    }

    public void checkAvailability(List<Reservation> reservations) {
        flight.checkAvailability(reservations, numberOfTickets);
    }


    public String flightNumber() {
        return flight.flightNumber();
    }

    public String passengerId() {
        return passenger.id();
    }
}
