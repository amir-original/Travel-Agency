package travelAgency.model.flight;

import java.util.Objects;

public class FlightCapacity {
    private final int capacity;

    private FlightCapacity(int capacity) {
        if (capacity <= 0) {
            throw InvalidFlightCapacity.becauseItIsNegative();
        }

        this.capacity = capacity;
    }

    public static FlightCapacity of(int capacity) {
        return new FlightCapacity(capacity);
    }

    public int capacity() {
        return capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightCapacity that = (FlightCapacity) o;
        return capacity == that.capacity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(capacity);
    }

    @Override
    public String toString() {
        return "FlightCapacity{" +
                "capacity=" + capacity +
                '}';
    }
}
