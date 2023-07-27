package travelAgency.application.use_case;

import travelAgency.model.passenger.Passenger;
import travelAgency.model.reservation.ReservationInformation;
import travelAgency.model.reservation.Reservation;
import travelAgency.model.reservation.ReservationRepository;
import travelAgency.model.passenger.PassengerRepository;
import travelAgency.application.dto.ReservationMapper;
import travelAgency.model.reservation.ReservationNumber;

// application service
public final class BookingReservation {

    private final ReservationRepository reservations;
    private final PassengerRepository passengers;

    private final FindReservationService findReservationService;
    public final ReservationNumberGenerator ReservationNumber;
    private final ReservationMapper reservationMapper;

    public BookingReservation(ReservationRepository reservations,
                              PassengerRepository passengers,
                              FindReservationService findReservationService,
                              ReservationNumberGenerator ReservationNumber) {

        this.reservations = reservations;
        this.passengers = passengers;
        this.findReservationService = findReservationService;
        this.ReservationNumber = ReservationNumber;

        this.reservationMapper = new ReservationMapper();
    }

    public Reservation book(ReservationInformation resInfo) {
        final ReservationNumber reservationNumber = ReservationNumber.generateReservationNumber();
        final Reservation reservation = reservationMapper.toEntity(resInfo, reservationNumber);
        int availableSeats = findReservationService.availableSeats(reservation.flightNumber());

        final Passenger passenger = reservation.passenger();
        reservation.ensureCanBooking(availableSeats);

        passengers.save(passenger);
        reservations.book(reservation);
        return reservation;
    }

}
