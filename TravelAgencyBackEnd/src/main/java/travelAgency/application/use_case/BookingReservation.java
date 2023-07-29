package travelAgency.application.use_case;

import travelAgency.application.dto.ReservationResponse;
import travelAgency.infrastructure.mapper.PassengerMapper;
import travelAgency.model.passenger.Passenger;
import travelAgency.application.dto.ReservationInformation;
import travelAgency.model.reservation.Reservation;
import travelAgency.model.reservation.ReservationRepository;
import travelAgency.model.passenger.PassengerRepository;
import travelAgency.infrastructure.mapper.ReservationMapper;
import travelAgency.model.reservation.ReservationNumber;

// application service
public final class BookingReservation {

    private final ReservationRepository reservations;
    private final PassengerRepository passengers;
    private final SearchReservationService searchReservationService;
    public final ReservationNumberGenerator ReservationNumber;
    private final ReservationMapper reservationMapper;
    private final PassengerMapper passengerMapper;

    public BookingReservation(ReservationRepository reservations,
                              PassengerRepository passengers,
                              SearchReservationService searchReservationService,
                              ReservationNumberGenerator ReservationNumber) {

        this.reservations = reservations;
        this.passengers = passengers;
        this.searchReservationService = searchReservationService;
        this.ReservationNumber = ReservationNumber;

        this.reservationMapper = new ReservationMapper();
        this.passengerMapper = new PassengerMapper();
    }

    public ReservationResponse book(ReservationInformation resInfo) {
        final ReservationNumber reservationNumber = ReservationNumber.generateReservationNumber();
        int availableSeats = searchReservationService.availableSeats(resInfo.getFlightNumber());

        final Reservation reservation = reservationMapper.toEntity(resInfo, reservationNumber);
        Passenger passenger = passengerMapper.toEntity(resInfo.passengerDto());
        reservation.ensureCanBooking(availableSeats);

        passengers.enroll(passenger);
        reservations.book(reservation);
        return reservationMapper.toView(reservation);
    }

}
