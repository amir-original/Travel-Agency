package travelAgency.fakeData;

import travelAgency.domain.*;

import java.util.Random;

import static java.time.LocalDate.of;
import static travelAgency.domain.city.City.PARIS;
import static travelAgency.domain.city.City.TEHRAN;
import static travelAgency.fakeData.FakeFlightBuilder.flight;
import static travelAgency.fakeData.FakePassengerBuilder.passenger;

public class FakeFlightTicketBuilder {

    private static final String EXIST_FLIGHT_NUMBER = "0321";
    private static final String NOT_EXIST_FLIGHT_NUMBER = "not_exist_serial_number";

    private Flight flight = getFlightWith(EXIST_FLIGHT_NUMBER);

    private Passenger passenger = passenger().build();

    private int numberOfTickets = 2;

    public static FakeFlightTicketBuilder flightTicket() {
        return new FakeFlightTicketBuilder();
    }

    public FakeFlightTicketBuilder withFlight(Flight flight) {
        this.flight = flight;
        return this;
    }

    public FakeFlightTicketBuilder withNotFoundFlight() {
        this.flight = getFlightWith(NOT_EXIST_FLIGHT_NUMBER);
        return this;
    }

    public FakeFlightTicketBuilder withPassenger(Passenger passenger) {
        this.passenger = passenger;
        return this;
    }

    public FakeFlightTicketBuilder withNumbers(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
        return this;
    }

    public FlightTicket build() {
        final BookingInformation bookingInformation = new BookingInformation(passenger, numberOfTickets);
        final FlightTicketInfo flightTicketInfo = new FlightTicketInfo(flight, bookingInformation);
        return new FlightTicket(generateTicketId(), flightTicketInfo);
    }

    private String generateTicketId() {
        final Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            result.append(random.nextInt(10));
        }
        return result.toString();
    }

    private Flight getFlightWith(String serialNumber) {
        return flight().withFlightNumber(serialNumber)
                .from(TEHRAN)
                .to(PARIS)
                .departureAt(of(2023, 3, 3))
                .arrivalAt(of(2023, 3, 6)).build();
    }
}
