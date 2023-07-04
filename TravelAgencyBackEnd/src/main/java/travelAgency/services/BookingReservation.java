package travelAgency.services;

import travelAgency.domain.passenger.Passenger;
import travelAgency.domain.reservation.ReservationInformation;
import travelAgency.domain.reservation.Reservation;
import travelAgency.dao.database.reservation.ReservationListRepository;
import travelAgency.dao.database.passenger.PassengerRepository;
import travelAgency.domain.reservation.ReservationMapper;
import travelAgency.domain.reservation.ReservationNumber;
import travelAgency.services.flight.FlightAvailability;
import travelAgency.services.reservation.ReservationNumberGenerator;

// application service
public final class BookingReservation {

    private final ReservationListRepository bookingLists;
    private final PassengerRepository passengers;
    private final FlightAvailability flightAvailability;
    public final ReservationNumberGenerator ReservationNumber;
    private final ReservationMapper reservationMapper;

    public BookingReservation(ReservationListRepository bookingLists,
                              PassengerRepository passengers, FlightAvailability flightAvailability,
                              ReservationNumberGenerator ReservationNumber) {

        this.bookingLists = bookingLists;
        this.passengers = passengers;
        this.ReservationNumber = ReservationNumber;
        this.flightAvailability =  flightAvailability;
        this.reservationMapper = new ReservationMapper();
    }

    public Reservation book(ReservationInformation resInfo) {
        final ReservationNumber reservationNumber = ReservationNumber.generateReservationNumber();
        final Reservation reservation = reservationMapper.toEntity(resInfo, reservationNumber);

        final Passenger passenger = reservation.passenger();

        flightAvailability.ensureCanBooking(reservation);
        passengers.save(passenger);
        bookingLists.book(reservation);
        return reservation;
    }

}
