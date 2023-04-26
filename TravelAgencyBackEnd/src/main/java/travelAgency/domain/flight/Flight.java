package travelAgency.domain.flight;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.city.City;
import travelAgency.domain.flight.currency.Money;

import java.time.LocalDate;

public record Flight(@NotNull String flightNumber,
                     int totalCapacity,
                     Money price,
                     @NotNull FlightPlan plan) {

    public Flight(@NotNull String flightNumber,
                  int totalCapacity,
                  Money price,
                  @NotNull FlightPlan plan) {
        this.flightNumber = flightNumber;
        this.totalCapacity = totalCapacity;
        this.price = price;
        this.plan = plan;
        FlightValidator flightValidator = new FlightValidator(this);
        flightValidator.validate();
    }

    public boolean match(Flight flight) {
        return flight.equals(this);
    }

    public boolean hasSameFlightNumber(String flightNumber) {
        return this.flightNumber.equals(flightNumber);
    }

    public boolean hasSameFlightPlan(FlightPlan searchFlightPlan) {
        return plan.hasSameFlightPlan(searchFlightPlan);
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

    public FlightSchedule schedule() {
        return plan.schedule();
    }

    public void validateScheduleNotInPast() {
        schedule().validate();
    }
}
