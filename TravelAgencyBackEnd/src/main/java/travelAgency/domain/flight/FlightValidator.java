package travelAgency.domain.flight;

import travelAgency.domain.exceptions.FlightNumberException;
import travelAgency.domain.exceptions.FlightPriceException;

public class FlightValidator {

    private final Flight flight;

    public FlightValidator(Flight flight) {
        this.flight = flight;
    }

    public void validate() {
        if (flight.flightNumber().isBlank() || flight.flightNumber().length() < 3)
            throw new FlightNumberException();
        if (isNegativePrice()) throw new FlightPriceException();
    }

    private boolean isNegativePrice() {
        return flight.price() <= 0;
    }
}
