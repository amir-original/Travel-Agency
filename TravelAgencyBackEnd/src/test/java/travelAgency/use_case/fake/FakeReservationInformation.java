package travelAgency.use_case.fake;

import travelAgency.application.dto.PassengerDto;
import travelAgency.infrastructure.mapper.PassengerMapper;
import travelAgency.application.dto.FlightDto;
import travelAgency.infrastructure.mapper.FlightMapper;
import travelAgency.model.reservation.ReservationInformation;
import travelAgency.model.flight.Flight;
import travelAgency.model.passenger.Passenger;

import static travelAgency.use_case.fake.FakeFlight.flight;
import static travelAgency.use_case.fake.FakePassenger.passenger;

public class FakeReservationInformation {

    private static final String EXIST_FLIGHT_NUMBER = "0321";
    private static final String NOT_EXIST_FLIGHT_NUMBER = "Not Found Flight Number";

    private Flight flight = flight(EXIST_FLIGHT_NUMBER);

    private Passenger passenger = passenger().build();

    private int numberOfTickets = 2;

    public static FakeReservationInformation reservationInformation() {
        return new FakeReservationInformation();
    }

    public FakeReservationInformation withFlight(Flight flight) {
        this.flight = flight;
        return this;
    }

    public FakeReservationInformation withNotFoundFlight() {
        this.flight = flight(NOT_EXIST_FLIGHT_NUMBER);
        return this;
    }

    public FakeReservationInformation withPassenger(Passenger passenger) {
        this.passenger = passenger;
        return this;
    }

    public FakeReservationInformation withTravelers(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
        return this;
    }

    public ReservationInformation build() {
        final FlightMapper flightMapper = new FlightMapper();
        final PassengerMapper passengerMapper = new PassengerMapper();

        final FlightDto flightDto = flightMapper.toViewDto(flight);
        final PassengerDto passengerDto = passengerMapper.toViewDto(passenger);
        return new ReservationInformation(flightDto, passengerDto, numberOfTickets);
    }

}
