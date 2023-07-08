package travelAgency.model.flight;

import org.jetbrains.annotations.NotNull;
import travelAgency.model.city.City;
import travelAgency.model.rate.currency.Money;
import travelAgency.exceptions.InvalidFlightNumberException;

import java.time.LocalDate;
import java.util.Objects;

public final class Flight {

    @NotNull
    private final String flightNumber;

    private final int totalCapacity;

    @NotNull
    private final Money price;

    @NotNull
    private final FlightPlan plan;
    private FlightStatus status = FlightStatus.SCHEDULED;


    private Flight(@NotNull String flightNumber,
                   @NotNull FlightPlan plan,
                   int totalCapacity,
                   @NotNull Money price) {
        if (flightNumber.isBlank() || flightNumber.length() < 3)
            throw new InvalidFlightNumberException();

        this.flightNumber = flightNumber;
        this.totalCapacity = totalCapacity;
        this.price = price;
        this.plan = plan;
    }

    public static Flight addWith(String flightNumber, FlightPlan flightPlan, int totalCapacity, Money price) {
        return new Flight(flightNumber, flightPlan, totalCapacity, price);
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

    public void markAsDeparted() {
        status = FlightStatus.DEPARTED;
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

    public String flightNumber() {
        return flightNumber;
    }

    public int totalCapacity() {
        return totalCapacity;
    }

    public Money price() {
        return price;
    }

    public FlightPlan plan() {
        return plan;
    }

    public boolean isDeparted() {
        return status.equals(FlightStatus.DEPARTED);
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
