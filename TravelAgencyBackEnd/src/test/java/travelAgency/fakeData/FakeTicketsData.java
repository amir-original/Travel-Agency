package travelAgency.fakeData;

import travelAgency.domain.FlightTicket;
import travelAgency.domain.Flight;
import travelAgency.domain.Passenger;

import java.util.List;

public class FakeTicketsData {
    private final FakeFlightData fakeFlight = new FakeFlightData();
    private final FakePassengerData fakePassenger = new FakePassengerData();

    public List<FlightTicket> getFakeBookingTickets() {
        return List.of(getExistTicket());
    }

    public FlightTicket getExistTicket() {
        return new FlightTicket(getFlight(), fakePassenger.getPassenger(), 2);
    }

    public FlightTicket getBookingInfoWithNullPassenger(){
        return new FlightTicket(getFlight(), null, 2);
    }

    public FlightTicket getBookingInfoWithNullFlight() {
        return new FlightTicket(null, fakePassenger.getPassenger(), 2);
    }

    public FlightTicket getNotFoundTicket() {
        return new FlightTicket(fakeFlight.getNotFoundFlight(), fakePassenger.getPassenger(), 2);
    }

    public FlightTicket getBookingInfoWithZeroOrNullTicket() {
        return new FlightTicket(getFlight(), fakePassenger.getPassenger(), 0);
    }

    public Flight getFlight() {
        return fakeFlight.getFakeFlightFromTehranToParis();
    }

    public Passenger getPassenger() {
        return fakePassenger.getPassenger();
    }
}
