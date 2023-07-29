package travelAgency.application.dto;

public record ReservationInformation(
        FlightDto flight,
        PassengerDto passengerDto,
        int numberOfTickets) {

    public String getFlightNumber() {
        return flight.flightNumber();
    }
}
