package travelAgency.domain.flight;

import travelAgency.domain.city.City;
import travelAgency.domain.rate.currency.Money;

import java.time.LocalDate;

public class FlightBuilder {

    private String flightNumber;
    private int totalCapacity;
    private Money price;
    private City from;
    private City to;
    private LocalDate departure;
    private LocalDate arrival;

    public static FlightBuilder flight() {
        return new FlightBuilder();
    }

    public FlightBuilder withFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
        return this;
    }

    public FlightBuilder from(City city) {
        this.from = city;
        return this;
    }

    public FlightBuilder to(City city) {
        this.to = city;
        return this;
    }

    public FlightBuilder departureAt(LocalDate date) {
        this.departure = date;
        return this;
    }

    public FlightBuilder arrivalAt(LocalDate date) {
        this.arrival = date;
        return this;
    }

    public FlightBuilder withTotalCapacity(int numberOfSeats) {
        this.totalCapacity = numberOfSeats;
        return this;
    }

    public FlightBuilder withPrice(Money price) {
        this.price = price;
        return this;
    }

    public Flight build() {
        final FlightSchedule schedule = new FlightSchedule(departure, arrival);
        final FlightLocation location = new FlightLocation(from, to);
        final FlightPlan flightPlan = new FlightPlan(location, schedule);
        return Flight.addWith(flightNumber, flightPlan, totalCapacity, price);
    }
}
