package travelAgency.model.flight;

import org.jetbrains.annotations.NotNull;
import travelAgency.exceptions.InvalidFlightNumberException;

public class FlightNumber {
    @NotNull
    private final String number;

    private FlightNumber(String number) {
        if (number.isBlank() || number.length() < 3)
            throw new InvalidFlightNumberException();

        this.number = number;
    }

    public static FlightNumber of(String number){
        return new FlightNumber(number);
    }

    public String toText() {
        return number;
    }
}
