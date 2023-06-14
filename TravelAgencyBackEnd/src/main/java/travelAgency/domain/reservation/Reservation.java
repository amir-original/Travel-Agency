package travelAgency.domain.reservation;

import org.jetbrains.annotations.NotNull;
import travelAgency.exceptions.InvalidNumberOfTicketsException;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.passenger.Passenger;

import java.time.LocalDate;
import java.util.Objects;

import static java.lang.String.format;

public final class Reservation {
    private final @NotNull ReservationNumber reservationNumber;
    private final @NotNull Flight flight;
    private final @NotNull Passenger passenger;
    private final int numberOfTickets;


    private Reservation(@NotNull ReservationNumber initReservationNumber,
                        @NotNull Flight flight,
                        @NotNull Passenger passenger,
                        int numberOfTickets) {

        if (hasNoTickets(numberOfTickets)) throw new InvalidNumberOfTicketsException();

        this.reservationNumber = initReservationNumber;
        this.flight = flight;
        this.passenger = passenger;
        this.numberOfTickets = numberOfTickets;
    }

    public static Reservation make(ReservationNumber reservationNumber, Flight flight, Passenger passenger, int numberOfTickets) {
        return new Reservation(reservationNumber,flight,passenger,numberOfTickets);
    }

    private boolean hasNoTickets(int numberOfTickets) {
        return numberOfTickets <= 0;
    }

    public boolean canMatchWith(String flightNumber,
                                String passengerFirstName,
                                LocalDate passengerBirthday) {
        return flight.hasSameFlightNumber(flightNumber) &&
                passenger.canMatchWith(passengerFirstName, passengerBirthday);
    }

    public boolean canMatchWith(String searchFlightNumber) {
        return flight.hasSameFlightNumber(searchFlightNumber);
    }

    public boolean hasSameTicketNumber(String ticketNumber) {
        return this.reservationNumber.toText().equals(ticketNumber);
    }

    public Flight flight() {
        return flight;
    }

    public String flightNumber() {
        return flight.flightNumber();
    }

    public String passengerId() {
        return passenger.passengerId().getId();
    }

    public int travelers() {
        return numberOfTickets;
    }

    public Passenger passenger() {
        return passenger;
    }

    public @NotNull String reservationNumber() {
        return reservationNumber.toText();
    }

    public String buildTicket() {
        return format(""" 
                        Passenger Name: %s               Flight Number: %s           Ticket Number: %s  
                                From : %s  📍 ---------------------------------------------- ✈  To: %s
                        Departure: %s                    Arrival: %s                 Price: %s     
                        """,
                passenger.fullName().toText(),
                flightNumber(),
                reservationNumber.toText(),
                flight.from(),
                flight.to(),
                flight.departure(),
                flight.arrival(),
                flight.price().formatMoneyWithSymbol());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return numberOfTickets == that.numberOfTickets &&
                reservationNumber.equals(that.reservationNumber) && flight.equals(that.flight) && passenger.equals(that.passenger);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationNumber, flight, passenger, numberOfTickets);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationNumber='" + reservationNumber + '\'' +
                ", flight=" + flight +
                ", passenger=" + passenger +
                ", numberOfTickets=" + numberOfTickets +
                '}';
    }
}
