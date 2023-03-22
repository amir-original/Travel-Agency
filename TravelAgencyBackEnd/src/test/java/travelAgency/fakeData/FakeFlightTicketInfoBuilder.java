package travelAgency.fakeData;

import travelAgency.domain.booking.BookingInformation;
import travelAgency.domain.booking.FlightTicketInfo;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.passenger.Passenger;

import static java.time.LocalDate.of;
import static travelAgency.domain.city.City.PARIS;
import static travelAgency.domain.city.City.TEHRAN;
import static travelAgency.fakeData.FakeFlightBuilder.flight;
import static travelAgency.fakeData.FakePassengerBuilder.passenger;

public class FakeFlightTicketInfoBuilder {

    private static final String EXIST_FLIGHT_NUMBER = "0321";
    private static final String NOT_EXIST_FLIGHT_NUMBER = "Not Found Flight Number";

    private Flight flight = getFlightWith(EXIST_FLIGHT_NUMBER);

    private Passenger passenger = passenger().build();

    private int numberOfTickets = 2;

    public static FakeFlightTicketInfoBuilder flightTicketInfo() {
        return new FakeFlightTicketInfoBuilder();
    }

    public FakeFlightTicketInfoBuilder withFlight(Flight flight) {
        this.flight = flight;
        return this;
    }

    public FakeFlightTicketInfoBuilder withNotFoundFlight() {
        this.flight = getFlightWith(NOT_EXIST_FLIGHT_NUMBER);
        return this;
    }

    public FakeFlightTicketInfoBuilder withPassenger(Passenger passenger) {
        this.passenger = passenger;
        return this;
    }

    public FakeFlightTicketInfoBuilder withNumbers(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
        return this;
    }

    public FlightTicketInfo build() {
        final BookingInformation bookingInformation = new BookingInformation(passenger, numberOfTickets);
        return new FlightTicketInfo(flight, bookingInformation);
    }

    private Flight getFlightWith(String serialNumber) {
        return flight().withFlightNumber(serialNumber)
                .from(TEHRAN)
                .to(PARIS)
                .departureAt(of(2023, 3, 3))
                .arrivalAt(of(2023, 3, 6)).build();
    }
}
