package travelAgency.model.reservation;

import org.jetbrains.annotations.NotNull;
import travelAgency.exceptions.CouldNotCancelReservation;
import travelAgency.exceptions.FullyBookedException;
import travelAgency.exceptions.InvalidNumberOfTicketsException;
import travelAgency.exceptions.NotEnoughCapacityException;
import travelAgency.model.flight.Flight;
import travelAgency.model.passenger.Passenger;
import travelAgency.model.flight.Money;

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

    public static Reservation make(ReservationNumber reservationNumber, Flight flight,
                                   Passenger passenger, int numberOfTickets) {
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

    public void ensureCanCancel() {
        if (isFlightDeparted()){
            throw CouldNotCancelReservation.becauseFlightIsDeparted();
        }
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

    public String from() {
        return flight.from().name();
    }

    public String to(){
        return flight.to().name();
    }

    public LocalDate departureDate() {
        return flight.departure();
    }

    public LocalDate arrivalDate(){
        return flight.arrival();
    }

    public Money price(){
        return flight.price();
    }

    public @NotNull String reservationNumber() {
        return reservationNumber.toText();
    }


    public String passengerFullName() {
      return   passenger.fullName().toText();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return numberOfTickets == that.numberOfTickets &&
                reservationNumber.equals(that.reservationNumber) &&
                flight.equals(that.flight) && passenger.equals(that.passenger);
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
