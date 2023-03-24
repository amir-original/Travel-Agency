package travelAgency.domain.flight;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.city.City;
import travelAgency.domain.exceptions.FlightNumberException;
import travelAgency.domain.exceptions.FlightPriceException;
import travelAgency.services.priceConverter.CurrencyConverterService;

import java.time.LocalDate;
import java.util.Objects;

public record Flight(@NotNull String flightNumber, double price, @NotNull FlightPlan plan) {

    public boolean matches(FlightPlan flightPlan) {
        return plan.equals(flightPlan);
    }

    public boolean matches(String flightNumber) {
        return flightNumber().equals(flightNumber);
    }

    public void check() {
        if (flightNumber.isBlank() || flightNumber.length() < 3)
            throw new FlightNumberException();

        if (price <= 0) throw new FlightPriceException();
    }

    public double price(CurrencyConverterService currencyConverter) {
        return currencyConverter.convert(price);
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
        return plan.arrival();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Double.compare(flight.price, price) == 0 &&
                Objects.equals(flightNumber, flight.flightNumber) &&
                Objects.equals(plan, flight.plan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightNumber, price, plan);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "name='" + flightNumber + '\'' +
                ", price=" + price +
                ", spec=" + plan +
                '}';
    }
}
