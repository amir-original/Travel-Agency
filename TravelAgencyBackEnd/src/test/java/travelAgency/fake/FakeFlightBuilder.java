package travelAgency.fake;

import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightPlan;

public class FakeFlightBuilder{

    private String flightNumber = "0321";
    private int totalCapacity = 40;
    private double price = 145;
    private FlightPlan flightPlan = FakeFlightPlanBuilder.flightPlan().build();


    public static FakeFlightBuilder flight() {
        return new FakeFlightBuilder();
    }

    public FakeFlightBuilder withFlightNumber(String serialNumber) {
        this.flightNumber = serialNumber;
        return this;
    }

    public FakeFlightBuilder withPlan(FlightPlan flightPlan){
        this.flightPlan = flightPlan;
        return this;
    }


    public FakeFlightBuilder withTotalCapacity(int numberOfSeats) {
        this.totalCapacity = numberOfSeats;
        return this;
    }

    public FakeFlightBuilder withPrice(double price) {
        this.price = price;
        return this;
    }

    public Flight build() {
        return new Flight(flightNumber, totalCapacity, price, flightPlan);
    }
}
