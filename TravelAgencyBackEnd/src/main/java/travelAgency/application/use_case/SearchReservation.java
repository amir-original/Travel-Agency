package travelAgency.application.use_case;

import travelAgency.application.dto.ReservationResponse;
import travelAgency.exceptions.ReservationNotFoundException;
import travelAgency.infrastructure.mapper.ReservationMapper;
import travelAgency.model.passenger.Birthdate;
import travelAgency.model.reservation.ReservationRepository;
import travelAgency.application.dto.FlightDto;
import travelAgency.model.reservation.Reservation;
import travelAgency.exceptions.CouldNotFoundReservation;

import java.time.LocalDate;

public final class SearchReservation implements SearchReservationService {

    public static final int NO_BOOKINGS = 0;
    private final ReservationRepository reservations;
    private final FindFlightService flights;
    private final ReservationMapper reservationMapper;


    public SearchReservation(ReservationRepository reservations, FindFlightService flights) {
        this.reservations = reservations;
        this.flights = flights;
        reservationMapper = new ReservationMapper();
    }

    @Override
    public ReservationResponse search(String flightNumber, String passengerFirstName, LocalDate passengerBirthday) {
        Reservation searchedReservation = reservations.getReservations().stream()
                .filter(reservation -> reservation.canMatchWith(flightNumber, passengerFirstName, passengerBirthday))
                .findFirst()
                .orElseThrow(ReservationNotFoundException::new);

        return reservationMapper.toView(searchedReservation);
    }

    @Override
    public ReservationResponse search(String reservationNumber) {
        Reservation reservation = reservations.findReservation(reservationNumber)
                .orElseThrow(CouldNotFoundReservation::new);
        return reservationMapper.toView(reservation);
    }

    @Override
    public FlightDto findFlight(String flightNumber) {
        return flights.findFlight(flightNumber);
    }

    @Override
    public int availableSeats(String flightNumber) {
        return findFlight(flightNumber).getTotalCapacity()
                - totalBookedSeats(flightNumber);
    }

    @Override
    public int totalBookedSeats(String flightNumber) {
        return this.reservations.getReservations().isEmpty() ? NO_BOOKINGS :
                calculateTotalBookedSeats(flightNumber);
    }

    private int calculateTotalBookedSeats(String flightNumber) {
        return reservations.getReservations().stream()
                .filter(reservation -> reservation.canMatchWith(flightNumber))
                .mapToInt(Reservation::travelers)
                .sum();
    }
}
