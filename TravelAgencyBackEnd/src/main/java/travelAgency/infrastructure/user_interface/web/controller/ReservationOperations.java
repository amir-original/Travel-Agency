package travelAgency.infrastructure.user_interface.web.controller;

import travelAgency.application.dto.ReservationResponse;
import travelAgency.model.reservation.Reservation;
import travelAgency.application.dto.ReservationInformation;

import java.time.LocalDate;

public interface ReservationOperations {
    ReservationResponse book(ReservationInformation reservationInformation);
    void cancel(String reservationNumber);
    ReservationResponse search(String flightNumber, String passengerFirstName, LocalDate PassengerBirthday);
    ReservationResponse search(String reservationNumber);
}
