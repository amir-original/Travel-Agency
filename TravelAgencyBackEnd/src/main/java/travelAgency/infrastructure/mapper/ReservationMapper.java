package travelAgency.infrastructure.mapper;

import travelAgency.model.flight.Flight;
import travelAgency.model.passenger.Passenger;
import travelAgency.model.reservation.Reservation;
import travelAgency.model.reservation.ReservationInformation;
import travelAgency.model.reservation.ReservationNumber;

public class ReservationMapper {

    public Reservation toEntity(ReservationInformation reservationInformation,
                                ReservationNumber reservationNumber){

        final FlightMapper flightMapper = new FlightMapper();
        final Flight flight = flightMapper.toEntity(reservationInformation.getFlight());
        final PassengerMapper passengerMapper = new PassengerMapper();
        final Passenger passenger = passengerMapper.toEntity(reservationInformation.getPassengerDto());
        return Reservation.make(reservationNumber,flight, passenger,reservationInformation.getNumberOfTickets());
    }
}
