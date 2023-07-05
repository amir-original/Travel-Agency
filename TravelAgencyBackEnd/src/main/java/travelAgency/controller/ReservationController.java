package travelAgency.controller;

import travelAgency.dao.database.passenger.PassengerRepository;
import travelAgency.dao.database.reservation.ReservationListRepository;
import travelAgency.domain.reservation.Reservation;
import travelAgency.domain.reservation.ReservationInformation;
import travelAgency.services.BookingReservation;
import travelAgency.services.ReservationCancellation;
import travelAgency.services.flight.FlightAvailability;
import travelAgency.services.flight.FlightListService;
import travelAgency.services.reservation.ReservationListService;
import travelAgency.services.reservation.ReservationListServiceImpl;
import travelAgency.services.reservation.ReservationNumberGenerator;

import java.time.LocalDate;

public class ReservationController implements ReservationOperations {

    private final BookingReservation bookingReservation;
    private final ReservationCancellation reservationCancellation;
    private final ReservationListService reservationListService;

    public ReservationController(ReservationListRepository reservations,
                                 PassengerRepository passengers,
                                 FlightAvailability flightAvailability,
                                 ReservationNumberGenerator reservationNumber,
                                 FlightListService flightsService) {
        this.bookingReservation =
                new BookingReservation(reservations, passengers, flightAvailability, reservationNumber);
        this.reservationCancellation = new ReservationCancellation(reservations);
        this.reservationListService = new ReservationListServiceImpl(reservations, flightsService);

    }

    public Reservation book(ReservationInformation reservationInformation) {
       return bookingReservation.book(reservationInformation);
    }

    public void cancel(String reservationNumber) {
        reservationCancellation.cancelReservation(reservationNumber);
    }

    public Reservation search(String flightNumber,String passengerFirstName,LocalDate PassengerBirthday){
        return reservationListService.search(flightNumber,passengerFirstName,PassengerBirthday);
    }


    public Reservation search(String reservationNumber) {
        return reservationListService.search(reservationNumber);
    }
}
