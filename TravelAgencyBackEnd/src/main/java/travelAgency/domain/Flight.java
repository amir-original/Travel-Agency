package travelAgency.domain;

import travelAgency.domain.exceptions.FlightInfoNotNullException;
import travelAgency.domain.exceptions.NotFindAnyFlightException;

import java.util.Objects;

public class Flight {
    private  String name;
    private double price;
    private final FlightTransit transfer;
    private final FlightSchedule flightSchedule;

    public Flight(FlightTransit transfer, FlightSchedule flightSchedule) {
        this.transfer = transfer;
        this.flightSchedule = flightSchedule;
    }

    public Flight(String name, FlightTransit transfer, FlightSchedule flightSchedule, double price) {
        this.name = name;
        this.transfer = transfer;
        this.price = price;
        this.flightSchedule = flightSchedule;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public void check(Flight flight) {
        if (isNotEqual(flight))
            throw new NotFindAnyFlightException();
    }

    private boolean isNotEqual(Flight flight) {
        return !this.equals(flight);
    }

    private boolean isNull(Object object) {
        return object == null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Double.compare(flight.price, price) == 0 && Objects.equals(name, flight.name) &&
                Objects.equals(flightSchedule, flight.flightSchedule) &&
                Objects.equals(transfer, flight.transfer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, flightSchedule, price, transfer);
    }

    public boolean like(Flight o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return  Objects.equals(transfer, flight.transfer) &&
                 Objects.equals(flightSchedule, flight.flightSchedule);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", transfer=" + transfer +
                ", flightSchedule=" + flightSchedule +
                '}';
    }
}
