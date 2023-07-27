package travelAgency.infrastructure.user_interface.web.controller;

import travelAgency.model.passenger.PassengerRepository;
import travelAgency.model.reservation.ReservationRepository;
import travelAgency.model.reservation.Reservation;
import travelAgency.model.reservation.ReservationInformation;
import travelAgency.application.use_case.BookingReservation;
import travelAgency.application.use_case.CancelReservation;
import travelAgency.application.use_case.FlightAvailability;
import travelAgency.application.use_case.FindFlightService;
import travelAgency.application.use_case.FindReservationService;
import travelAgency.application.use_case.FindReservation;
import travelAgency.application.use_case.ReservationNumberGenerator;

import java.time.LocalDate;

public class ReservationController implements ReservationOperations {

    private final BookingReservation bookingReservation;
    private final CancelReservation cancelReservation;
    private final FindReservationService findReservationService;

    public ReservationController(ReservationRepository reservations,
                                 PassengerRepository passengers,
                                 FindReservationService findReservationService,
                                 ReservationNumberGenerator reservationNumber,
                                 FindFlightService flightsService) {
        this.bookingReservation =
                new BookingReservation(reservations, passengers, findReservationService,reservationNumber);
        this.cancelReservation = new CancelReservation(reservations);
        this.findReservationService = new FindReservation(reservations, flightsService);

    }

    public Reservation book(ReservationInformation reservationInformation) {
       return bookingReservation.book(reservationInformation);
    }

    public void cancel(String reservationNumber) {
        cancelReservation.cancelReservation(reservationNumber);
    }

    public Reservation search(String flightNumber,String passengerFirstName,LocalDate PassengerBirthday){
        return findReservationService.search(flightNumber,passengerFirstName,PassengerBirthday);
    }


    public Reservation search(String reservationNumber) {
        return findReservationService.search(reservationNumber);
    }
}
