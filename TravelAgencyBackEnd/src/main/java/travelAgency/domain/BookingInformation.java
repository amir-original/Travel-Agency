package travelAgency.domain;

import travelAgency.domain.exceptions.FlightInfoNotNullException;
import travelAgency.domain.exceptions.PassengerNotNullException;
import travelAgency.domain.exceptions.TicketNumberNotZeroException;

public record BookingInformation(Flight flight, Passenger passenger, int numberOfTickets) {

    public void check() {
        if (flight == null)
            throw new FlightInfoNotNullException();
        if (passenger == null)
            throw new PassengerNotNullException();
        if (numberOfTickets <= 0)
            throw new TicketNumberNotZeroException();
    }

    public FlightInformation flightInfo(){
        return flight.getInfo();
    }


}
