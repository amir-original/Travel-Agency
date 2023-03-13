package travelAgency.domain;

import travelAgency.domain.exceptions.NotFindAnyFlightException;

import java.util.Objects;

public class Flight {
    private final String serialNumber;
    private final double price;
    private final FlightPlan plan;

    public Flight(String serialNumber, double price, FlightPlan plan) {
        this.serialNumber = serialNumber;
        this.price = price;
        this.plan = plan;
    }

    public double getPrice() {
        return price;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public FlightPlan getPlan() {
        return plan;
    }

    public boolean matches(FlightPlan flightPlan) {
        return plan.equals(flightPlan);
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
        return Double.compare(flight.price, price) == 0 && Objects.equals(serialNumber, flight.serialNumber) && Objects.equals(plan, flight.plan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNumber, price, plan);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "name='" + serialNumber + '\'' +
                ", price=" + price +
                ", spec=" + plan +
                '}';
    }
}
