package travelAgency.domain.flight;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.booking.BookingInformation;
import travelAgency.domain.city.City;
import travelAgency.domain.exceptions.FlightNumberException;
import travelAgency.domain.exceptions.FlightPriceException;
import travelAgency.domain.exceptions.NotFindAnyFlightException;
import travelAgency.services.priceConverter.CurrencyConverterService;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public record Flight(@NotNull String flightNumber,
                     int totalCapacity,
                     double price,
                     @NotNull FlightPlan plan) {

    public Flight(@NotNull String flightNumber, int totalCapacity, double price, @NotNull FlightPlan plan) {
        this.flightNumber = flightNumber;
        this.totalCapacity = totalCapacity;
        this.price = price;
        this.plan = plan;
        validate();
    }

    public void validate() {
        if (flightNumber.isBlank() || flightNumber.length() < 3)
            throw new FlightNumberException();
        if (price <= 0) throw new FlightPriceException();
    }

    public void checkExistenceFlight(List<Flight> flights) {
        flights.stream()
                .filter(flight -> flight.matches(flightNumber))
                .findFirst()
                .orElseThrow(NotFindAnyFlightException::new);
    }

    public boolean matches(FlightPlan flightPlan) {
        return plan.equals(flightPlan);
    }

    public boolean matches(String flightNumber) {
        return flightNumber().equals(flightNumber);
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
