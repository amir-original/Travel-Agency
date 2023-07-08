package travelAgency.infrastructure.user_interface.web.controller;

import travelAgency.model.passenger.PassengerRepository;
import travelAgency.model.reservation.ReservationRepository;
import travelAgency.model.reservation.Reservation;
import travelAgency.model.reservation.ReservationInformation;
import travelAgency.application.reservation.BookingReservation;
import travelAgency.application.reservation.ReservationCancellation;
import travelAgency.application.flight.FlightAvailability;
import travelAgency.application.flight.FlightListService;
import travelAgency.application.reservation.ReservationListService;
import travelAgency.application.reservation.ReservationListServiceImpl;
import travelAgency.application.reservation.ReservationNumberGenerator;

import java.time.LocalDate;

public class ReservationController implements ReservationOperations {

    private final BookingReservation bookingReservation;
    private final ReservationCancellation reservationCancellation;
    private final ReservationListService reservationListService;

    public ReservationController(ReservationRepository reservations,
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
