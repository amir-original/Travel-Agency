package travelAgency.infrastructure.user_interface.web.controller;

import travelAgency.model.reservation.Reservation;
import travelAgency.model.reservation.ReservationInformation;

import java.time.LocalDate;

public interface ReservationOperations {
    Reservation book(ReservationInformation reservationInformation);
    void cancel(String reservationNumber);
    Reservation search(String flightNumber, String passengerFirstName, LocalDate PassengerBirthday);
    Reservation search(String reservationNumber);
}
