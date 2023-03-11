package travelAgency.domain;

import travelAgency.domain.exceptions.NotFindAnyFlightException;

import java.util.Objects;

public class Flight {
    private final String serialNumber;
    private final double price;
    private final FlightInformation info;

    public Flight(String serialNumber, double price, FlightInformation info) {
        this.serialNumber = serialNumber;
        this.price = price;
        this.info = info;
    }

    public double getPrice() {
        return price;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public FlightInformation getInfo() {
        return info;
    }

    public boolean isTheSameSpec(FlightInformation flightSpec) {
        return info.equals(flightSpec);
    }

    public void check(Flight flight) {
        if (isNotEqual(flight))
            throw new NotFindAnyFlightException();
    }

    private boolean isNotEqual(Flight flight) {
        return !isNull(flight);
    }

    private boolean isNull(Object object) {
        return object == null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Double.compare(flight.price, price) == 0 && Objects.equals(serialNumber, flight.serialNumber) && Objects.equals(info, flight.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNumber, price, info);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "name='" + serialNumber + '\'' +
                ", price=" + price +
                ", spec=" + info +
                '}';
    }
}
