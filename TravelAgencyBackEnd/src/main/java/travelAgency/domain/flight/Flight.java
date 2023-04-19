package travelAgency.domain.flight;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.city.City;
import travelAgency.services.priceConverter.CurrencyConverterService;

import java.time.LocalDate;

public record Flight(@NotNull String flightNumber,
                     int totalCapacity,
                     double price,
                     @NotNull FlightPlan plan) {

    public Flight(@NotNull String flightNumber, int totalCapacity, double price, @NotNull FlightPlan plan) {
        this.flightNumber = flightNumber;
        this.totalCapacity = totalCapacity;
        this.price = price;
        this.plan = plan;
        final FlightValidator flightValidator = new FlightValidator(this);
        flightValidator.validate();
    }

    public boolean match(FlightPlan flightPlan) {
        return plan.equals(flightPlan);
    }

    public boolean match(Flight flight) {
        return flight.equals(this);
    }

    public boolean hasSameFlightNumber(String flightNumber) {
        return this.flightNumber.equals(flightNumber);
    }

    public void validateSchedule() {
        plan.validateSchedule();
    }

    public String price(CurrencyConverterService currencyConverter) {
        return currencyConverter.convertAndFormat(price);
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

}
