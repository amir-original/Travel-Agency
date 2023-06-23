package travelAgency.controller;

import travelAgency.domain.reservation.Reservation;
import travelAgency.domain.reservation.ReservationInformation;

import java.time.LocalDate;

public interface ReservationOperations {
    Reservation book(ReservationInformation reservationInformation);
    void cancel(String reservationNumber);
    Reservation search(String flightNumber, String passengerFirstName, LocalDate PassengerBirthday);
    Reservation search(String reservationNumber);
}
