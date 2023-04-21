package travelAgency.domain.reservation;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.exceptions.FlightNotFoundException;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.passenger.Passenger;

import java.time.LocalDate;
import java.util.List;

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
        new ReservationInformationValidator(this).validate();
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

    public Flight findFlight(List<Flight> flights) {
        return flights.stream()
                .filter(this::isFlightAvailable)
                .findFirst()
                .orElseThrow(FlightNotFoundException::new);
    }

    private boolean isFlightAvailable(Flight flight) {
        return flight.hasSameFlightNumber(flightNumber());
    }

    public String flightNumber() {
        return flight.flightNumber();
    }

    public String passengerId() {
        return passenger.id();
    }
}
