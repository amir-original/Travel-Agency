package travelAgency.domain.reservation;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.exceptions.FlightNotFoundException;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.passenger.Passenger;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Stream.of;

public record BookingInformation(@NotNull Flight flight,
                                 @NotNull Passenger passenger,
                                 int numberOfTickets) {

    private static final int EMPTY_BOOKED = 0;

    public BookingInformation(@NotNull Flight flight,
                              @NotNull Passenger passenger,
                              int numberOfTickets) {
        this.flight = flight;
        this.passenger = passenger;
        this.numberOfTickets = numberOfTickets;
        new BookingInformationValidator(this).validate();
    }

    public Reservation getReservation(String ticketNumber) {
        return new Reservation(ticketNumber, this);
    }

    public boolean canMatchWith(String flightNumber, String passengerFirstName,
                                LocalDate passengerBirthday) {

        return flight.flightNumber().equals(flightNumber) &&
                passenger.canMatchWith(passengerFirstName, passengerBirthday);
    }

    public Flight findFlight(List<Flight> flights) {
        return flights.stream()
                .filter(this::isFlightAvailable)
                .findFirst()
                .orElseThrow(FlightNotFoundException::new);
    }

    private boolean isFlightAvailable(Flight flight) {
        return flight.match(flight());
    }


    public int getAvailableSeatsAfterBooking(List<Reservation> reservations) {
        return flight.totalCapacity() - getTotalBookingSeats(reservations);
    }

    private int getTotalBookingSeats(List<Reservation> reservations) {
        return numberOfTickets + getBookedSeats(reservations);
    }

    public int getAvailableSeats(List<Reservation> reservations) {
        return flight.totalCapacity() - getBookedSeats(reservations);
    }

    public int getBookedSeats(List<Reservation> reservations) {
        return reservations.isEmpty() ? EMPTY_BOOKED :
                reservations.stream()
                        .filter(this::reservationMatchesFlightNumber)
                        .mapToInt(Reservation::travelers)
                        .sum();
    }

    private boolean reservationMatchesFlightNumber(Reservation reservation) {
        return reservation.canMatchWith(flightNumber());
    }

    public String flightNumber() {
        return flight.flightNumber();
    }

    public String passengerId() {
        return passenger.id();
    }


   /* public Passenger getPassenger(String passengerId) {
        return new Passenger(passengerId);
    }*/
}
