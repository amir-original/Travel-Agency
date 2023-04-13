package travelAgency.use_case.fake;

import travelAgency.domain.booking.BookingInformation;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.passenger.Passenger;

import static travelAgency.use_case.fake.FakeFlight.flight;
import static travelAgency.use_case.fake.FakePassenger.passenger;

public class FakeBookingInformationBuilder {

    private static final String EXIST_FLIGHT_NUMBER = "0321";
    private static final String NOT_EXIST_FLIGHT_NUMBER = "Not Found Flight Number";

    private Flight flight = flight(EXIST_FLIGHT_NUMBER);

    private Passenger passenger = passenger().build();

    private int numberOfTickets = 2;

    public static FakeBookingInformationBuilder bookingInformation() {
        return new FakeBookingInformationBuilder();
    }

    public FakeBookingInformationBuilder withFlight(Flight flight) {
        this.flight = flight;
        return this;
    }

    public FakeBookingInformationBuilder withNotFoundFlight() {
        this.flight = flight(NOT_EXIST_FLIGHT_NUMBER);
        return this;
    }

    public FakeBookingInformationBuilder withPassenger(Passenger passenger) {
        this.passenger = passenger;
        return this;
    }

    public FakeBookingInformationBuilder withTravelers(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
        return this;
    }

    public BookingInformation build() {
        return new BookingInformation(flight, passenger, numberOfTickets);
    }

}
