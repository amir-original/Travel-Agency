package travelAgency.domain.reservation;

import travelAgency.domain.flight.Flight;
import travelAgency.domain.flight.FlightMapper;
import travelAgency.domain.passenger.Passenger;
import travelAgency.domain.passenger.PassengerMapper;

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
