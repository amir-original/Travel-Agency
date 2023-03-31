package travelAgency.fake;

import travelAgency.domain.booking.BookingInformation;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightPlan;
import travelAgency.domain.passenger.Passenger;

import java.time.LocalDate;

import static travelAgency.domain.city.City.PARIS;
import static travelAgency.domain.city.City.TEHRAN;
import static travelAgency.fake.FakeFlightBuilder.flight;
import static travelAgency.fake.FakePassenger.passenger;

public class FakeBookingInformationBuilder {

    private static final String EXIST_FLIGHT_NUMBER = "0321";
    private static final String NOT_EXIST_FLIGHT_NUMBER = "Not Found Flight Number";

    private Flight flight = getFlightWith(EXIST_FLIGHT_NUMBER);

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
        this.flight = getFlightWith(NOT_EXIST_FLIGHT_NUMBER);
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

    private Flight getFlightWith(String serialNumber) {
        final LocalDate now = LocalDate.now();
        final LocalDate tomorrow = LocalDate.now().plusDays(1);
        FlightPlan flightPlan = FakeFlightPlanBuilder.flightPlan().from(TEHRAN)
                .to(PARIS)
                .departureAt(now)
                .arrivalAt(tomorrow)
                .build();

        return flight()
                .withFlightNumber(serialNumber)
                .withPlan(flightPlan)
                .build();
    }
}
