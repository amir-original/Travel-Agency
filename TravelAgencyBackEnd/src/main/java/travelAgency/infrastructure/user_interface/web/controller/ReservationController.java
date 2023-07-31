package travelAgency.infrastructure.user_interface.web.controller;

import travelAgency.application.dto.ReservationResponse;
import travelAgency.model.passenger.PassengerRepository;
import travelAgency.model.reservation.ReservationRepository;
import travelAgency.model.reservation.Reservation;
import travelAgency.application.dto.ReservationInformation;
import travelAgency.application.use_case.BookingReservation;
import travelAgency.application.use_case.CancelReservation;
import travelAgency.application.use_case.FindFlightService;
import travelAgency.application.use_case.SearchReservationService;
import travelAgency.application.use_case.SearchReservation;
import travelAgency.application.use_case.ReservationNumberGenerator;

import java.time.LocalDate;

public class ReservationController implements ReservationOperations {

    private final BookingReservation bookingReservation;
    private final CancelReservation cancelReservation;
    private final SearchReservationService searchReservationService;

    public ReservationController(ReservationRepository reservations,
                                 PassengerRepository passengers,
                                 SearchReservationService searchReservationService,
                                 ReservationNumberGenerator reservationNumber,
                                 FindFlightService flightsService) {
        this.bookingReservation =
                new BookingReservation(reservations, passengers,
                        searchReservationService,reservationNumber);
        this.cancelReservation = new CancelReservation(reservations);
        this.searchReservationService = new SearchReservation(reservations, flightsService);

    }

    public ReservationResponse book(ReservationInformation reservationInformation) {
       return bookingReservation.book(reservationInformation);
    }

    public void cancel(String reservationNumber) {
        cancelReservation.cancelReservation(reservationNumber);
    }

    public ReservationResponse search(String flightNumber, String passengerFirstName, LocalDate PassengerBirthday){
        return searchReservationService.search(flightNumber,passengerFirstName,PassengerBirthday);
    }

    public ReservationResponse search(String reservationNumber) {
        return searchReservationService.search(reservationNumber);
    }
}
