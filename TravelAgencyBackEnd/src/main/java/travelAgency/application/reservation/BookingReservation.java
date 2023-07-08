package travelAgency.application.reservation;

import travelAgency.model.passenger.Passenger;
import travelAgency.model.reservation.ReservationInformation;
import travelAgency.model.reservation.Reservation;
import travelAgency.model.reservation.ReservationRepository;
import travelAgency.model.passenger.PassengerRepository;
import travelAgency.application.dto.ReservationMapper;
import travelAgency.model.reservation.ReservationNumber;
import travelAgency.application.flight.FlightAvailability;
import travelAgency.application.reservation.ReservationNumberGenerator;

// application service
public final class BookingReservation {

    private final ReservationRepository bookingLists;
    private final PassengerRepository passengers;
    private final FlightAvailability flightAvailability;
    public final ReservationNumberGenerator ReservationNumber;
    private final ReservationMapper reservationMapper;

    public BookingReservation(ReservationRepository bookingLists,
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
