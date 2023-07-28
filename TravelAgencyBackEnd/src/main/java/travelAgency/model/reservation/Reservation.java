package travelAgency.model.reservation;

import org.jetbrains.annotations.NotNull;
import travelAgency.exceptions.FullyBookedException;
import travelAgency.exceptions.InvalidNumberOfTicketsException;
import travelAgency.exceptions.NotEnoughCapacityException;
import travelAgency.model.flight.Flight;
import travelAgency.model.passenger.Birthdate;
import travelAgency.model.passenger.Passenger;

import java.time.LocalDate;
import java.util.Objects;

import static java.lang.String.format;

public final class Reservation {
    private static final int NO_AVAILABLE_SEATS = 0;
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

    public void ensureCanBooking(int availableSeats) {
        flight.validateScheduleNotInPast();
        checkFlightCapacity(availableSeats);
    }

    public void checkFlightCapacity(int availableSeats) {
        if (isSoldOutAllSeats(availableSeats))
            throw new FullyBookedException();
        if (hasNotEnoughCapacity(availableSeats))
            throw new NotEnoughCapacityException();
    }

    private boolean isSoldOutAllSeats(int availableSeats) {
        return availableSeats == NO_AVAILABLE_SEATS;
    }

    private boolean hasNotEnoughCapacity(int availableSeats) {
        return !hasEnoughCapacity(availableSeats);
    }

    private boolean hasEnoughCapacity(int availableSeats) {
        return availableSeats >= travelers();
    }

    public void markFlightAsDeparted() {
        flight.markAsDeparted();
    }

    public boolean isFlightDeparted() {
        return flight.isDeparted();
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

    @NotNull
    public Object[][] createReservationInfo() {
        return new Object[][]{
                {       reservationNumber(),
                        passenger().fullName().toText(),
                        flightNumber(),
                        flight.from(),
                        flight.to(),
                        travelers()
                }};
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
