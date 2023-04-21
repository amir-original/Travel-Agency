package travelAgency.use_case.fake;

import travelAgency.domain.reservation.ReservationInformation;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.passenger.Passenger;

import static travelAgency.use_case.fake.FakeFlight.flight;
import static travelAgency.use_case.fake.FakePassenger.passenger;

public class FakeReservationInformationBuilder {

    private static final String EXIST_FLIGHT_NUMBER = "0321";
    private static final String NOT_EXIST_FLIGHT_NUMBER = "Not Found Flight Number";

    private Flight flight = flight(EXIST_FLIGHT_NUMBER);

    private Passenger passenger = passenger().build();

    private int numberOfTickets = 2;

    public static FakeReservationInformationBuilder bookingInformation() {
        return new FakeReservationInformationBuilder();
    }

    public FakeReservationInformationBuilder withFlight(Flight flight) {
        this.flight = flight;
        return this;
    }

    public FakeReservationInformationBuilder withNotFoundFlight() {
        this.flight = flight(NOT_EXIST_FLIGHT_NUMBER);
        return this;
    }

    public FakeReservationInformationBuilder withPassenger(Passenger passenger) {
        this.passenger = passenger;
        return this;
    }

    public FakeReservationInformationBuilder withTravelers(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
        return this;
    }

    public ReservationInformation build() {
        return new ReservationInformation(flight, passenger, numberOfTickets);
    }

}
