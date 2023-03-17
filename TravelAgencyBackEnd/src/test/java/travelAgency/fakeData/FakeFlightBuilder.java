package travelAgency.fakeData;

import travelAgency.domain.*;
import travelAgency.domain.city.City;

import java.time.LocalDate;

import static java.time.LocalDate.of;

public class FakeFlightBuilder {

    private String flightNumber = "0321";
    private double price = 145;
    private City from = City.TEHRAN;
    private City to = City.PARIS;
    private LocalDate departure = of(2023, 3, 3);
    private LocalDate arrival = of(2023, 3, 6);

    public static FakeFlightBuilder flight() {
        return new FakeFlightBuilder();
    }

    public FakeFlightBuilder withFlightNumber(String serialNumber) {
        this.flightNumber = serialNumber;
        return this;
    }

    public FakeFlightBuilder from(City city) {
        this.from = city;
        return this;
    }

    public FakeFlightBuilder to(City city) {
        this.to = city;
        return this;
    }

    public FakeFlightBuilder departureAt(LocalDate date) {
        this.departure = date;
        return this;
    }

    public FakeFlightBuilder arrivalAt(LocalDate date) {
        this.arrival = date;
        return this;
    }

    public FakeFlightBuilder withPrice(double price) {
        this.price = price;
        return this;
    }

    public Flight build() {
        return new Flight(flightNumber, price,
                new FlightPlan(new FlightLocation(from, to), new FlightSchedule(departure, arrival)));
    }
}
