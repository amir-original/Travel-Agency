package travelAgency.services.reservation;

import travelAgency.dao.database.reservation.ReservationListRepository;
import travelAgency.domain.exceptions.ReservationNotFoundException;
import travelAgency.domain.reservation.Reservation;
import travelAgency.services.flight.FlightService;

import java.time.LocalDate;
import java.util.List;

public class ReservationListServiceImpl implements ReservationListService {

    public static final int NO_BOOKINGS = 0;
    private final ReservationListRepository reservations;
    private final SearchTicketEngine searchEngine;
    private final FlightService flights;


    public ReservationListServiceImpl(ReservationListRepository reservations, FlightService flights) {
        this.reservations = reservations;
        this.flights = flights;
        this.searchEngine = new SearchTicketEngine(getAllReservations());
    }

    @Override
    public Reservation search(String flightNumber, String passengerFirstName, LocalDate passengerBirthday) {
        return searchEngine.search(flightNumber, passengerFirstName, passengerBirthday);
    }

    @Override
    public void cancel(String ticketNumber) {
        final Reservation reservation = reservations.findReservation(ticketNumber)
                .orElseThrow(ReservationNotFoundException::new);

        reservations.cancel(reservation.ticketNumber());
    }

    @Override
    public int getTotalBookedSeats(String flightNumber) {
        return this.reservations.getReservations().isEmpty() ? NO_BOOKINGS :
                calculateTotalBookedSeats(flightNumber);
    }

    private int calculateTotalBookedSeats(String flightNumber) {
        return reservations.getReservations().stream()
                .filter(reservation -> reservation.canMatchWith(flightNumber))
                .mapToInt(Reservation::travelers)
                .sum();
    }

    @Override
    public int getAvailableSeats(String flightNumber) {
        return flights.getTotalCapacity(flightNumber) - getTotalBookedSeats(flightNumber);
    }

    private List<Reservation> getAllReservations() {
        return reservations.getReservations();
    }

}
