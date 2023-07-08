package travelAgency.model.reservation;

import travelAgency.application.dto.FlightDto;
import travelAgency.application.dto.PassengerDto;

public final class ReservationInformation {

    private final FlightDto flight;
    private final PassengerDto passengerDto;
    private final int numberOfTickets;

    public ReservationInformation(FlightDto flight,
                                  PassengerDto passengerDto,
                                  int numberOfTickets) {
        this.flight = flight;
        this.passengerDto = passengerDto;
        this.numberOfTickets = numberOfTickets;
    }

    public FlightDto getFlight() {
        return flight;
    }

    public PassengerDto getPassengerDto() {
        return passengerDto;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public String getFlightNumber() {
        return flight.getFlightNumber();
    }
}
