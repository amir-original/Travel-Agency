package travelAgency.domain;

import travelAgency.domain.city.City;
import travelAgency.services.priceConverter.CurrencyConverterService;

import java.net.ProtocolFamily;
import java.time.LocalDate;
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

    public double getPrice(CurrencyConverterService currencyConverter) {
        return currencyConverter.convert(price);
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

    public City to() {
        return plan.to();
    }

    public City from() {
        return plan.from();
    }

    public LocalDate departure() {
        return plan.departure();
    }

    public LocalDate arrival() {
        return   plan.arrival();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Double.compare(flight.price, price) == 0 &&
                Objects.equals(serialNumber, flight.serialNumber) &&
                Objects.equals(plan, flight.plan);
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
