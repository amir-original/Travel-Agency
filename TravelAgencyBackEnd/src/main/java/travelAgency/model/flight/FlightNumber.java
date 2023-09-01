package travelAgency.model.flight;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

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

    public boolean isEqual(String flightNumber) {
        return number.equals(flightNumber);
    }

    public String toText() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightNumber that = (FlightNumber) o;
        return number.equals(that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return "FlightNumber{" +
                "number='" + number + '\'' +
                '}';
    }
}
