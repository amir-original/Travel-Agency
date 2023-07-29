package travelAgency.infrastructure.mapper;

import travelAgency.application.dto.ReservationResponse;
import travelAgency.model.flight.Flight;
import travelAgency.model.passenger.Passenger;
import travelAgency.model.reservation.Reservation;
import travelAgency.application.dto.ReservationInformation;
import travelAgency.model.reservation.ReservationNumber;

public class ReservationMapper {

    public Reservation toEntity(ReservationInformation reservationInformation,
                                ReservationNumber reservationNumber){

        final FlightMapper flightMapper = new FlightMapper();
        final Flight flight = flightMapper.toEntity(reservationInformation.flight());
        final PassengerMapper passengerMapper = new PassengerMapper();
        final Passenger passenger = passengerMapper.toEntity(reservationInformation.passengerDto());
        return Reservation.make(reservationNumber,flight, passenger,reservationInformation.numberOfTickets());
    }

    public ReservationResponse toView(Reservation reservation){
        return new ReservationResponse(
                reservation.reservationNumber(),
                reservation.passengerId(),
                reservation.passengerFullName(),
                reservation.flightNumber(),
                reservation.from(),
                reservation.to(),
                reservation.departureDate().toString(),
                reservation.arrivalDate().toString(),
                reservation.price().formatMoneyWithSymbol(),
                reservation.travelers()
        );
    }


}
