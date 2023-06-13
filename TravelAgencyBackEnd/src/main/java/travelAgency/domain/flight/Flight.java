package travelAgency.domain.flight;

import org.jetbrains.annotations.NotNull;
import travelAgency.domain.city.City;
import travelAgency.domain.rate.currency.Money;
import travelAgency.exceptions.InvalidFlightNumberException;

import java.time.LocalDate;
import java.util.Objects;

public final class Flight {
    private final @NotNull String flightNumber;
    private final int totalCapacity;
    private final Money price;
    private final @NotNull FlightPlan plan;


    public Flight(@NotNull String flightNumber, int totalCapacity, Money price, @NotNull FlightPlan plan) {
        if (flightNumber.isBlank() || flightNumber.length() < 3)
            throw new InvalidFlightNumberException();

        this.flightNumber = flightNumber;
        this.totalCapacity = totalCapacity;
        this.price = price;
        this.plan = plan;
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

    public @NotNull String flightNumber() {
        return flightNumber;
    }

    public int totalCapacity() {
        return totalCapacity;
    }

    public Money price() {
        return price;
    }

    public @NotNull FlightPlan plan() {
        return plan;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Flight) obj;
        return Objects.equals(this.flightNumber, that.flightNumber) &&
                this.totalCapacity == that.totalCapacity &&
                Objects.equals(this.price, that.price) &&
                Objects.equals(this.plan, that.plan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightNumber, totalCapacity, price, plan);
    }

    @Override
    public String toString() {
        return "Flight[" +
                "flightNumber=" + flightNumber + ", " +
                "totalCapacity=" + totalCapacity + ", " +
                "price=" + price + ", " +
                "plan=" + plan + ']';
    }

}
