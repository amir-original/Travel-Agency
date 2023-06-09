package travelAgency.domain.flight;

import travelAgency.exceptions.InvalidFlightNumberException;

public class FlightValidator {

    private final Flight flight;

    public FlightValidator(Flight flight) {
        this.flight = flight;
    }

    public void validate() {
        if (flight.flightNumber().isBlank() || flight.flightNumber().length() < 3)
            throw new InvalidFlightNumberException();
        if (isNegativePrice()) throw new IllegalArgumentException("the price must not be negative!");
    }

    private boolean isNegativePrice() {
        return flight.price().amount() <= 0;
    }
}
