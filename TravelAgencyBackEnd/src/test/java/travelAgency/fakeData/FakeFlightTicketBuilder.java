package travelAgency.fakeData;

import travelAgency.domain.Flight;
import travelAgency.domain.FlightTicket;
import travelAgency.domain.Passenger;

import static java.time.LocalDate.of;
import static travelAgency.domain.city.City.PARIS;
import static travelAgency.domain.city.City.TEHRAN;
import static travelAgency.fakeData.FakeFlightBuilder.flight;

public class FakeFlightTicketBuilder {

    private static final String EXIST_SERIAL_NUMBER = "0321";
    private static final String NOT_EXIST_SERIAL_NUMBER = "not_exist_serial_number";

    private Flight flight = getFlightWith(EXIST_SERIAL_NUMBER);

    private Passenger passenger = new Passenger("fly78478", "Sara", "Baiati",
            of(1999, 4, 5),
            TEHRAN,"Iran,TEHRAN",
            "1145789", "989907994339");

    private int numberOfTickets = 2;

    public static FakeFlightTicketBuilder flightTicket(){
            return new FakeFlightTicketBuilder();
    }

    public FakeFlightTicketBuilder withFlight(Flight flight) {
        this.flight = flight;
        return this;
    }

    public FakeFlightTicketBuilder withNotFoundFlight() {
        this.flight = getFlightWith(NOT_EXIST_SERIAL_NUMBER);
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
        return new FlightTicket(flight, passenger, numberOfTickets);
    }

    private Flight getFlightWith(String serialNumber) {
        return flight().withSerialNumber(serialNumber)
                .from(TEHRAN)
                .to(PARIS)
                .departureAt(of(2023, 3, 3))
                .arrivalAt(of(2023, 3, 6)).build();
    }
}
