package travelAgency.use_case.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.model.reservation.Reservation;
import travelAgency.exceptions.CouldNotCancelReservation;
import travelAgency.exceptions.CouldNotFoundReservation;
import travelAgency.exceptions.ReservationNotFoundException;
import travelAgency.application.use_case.BookingReservation;
import travelAgency.application.use_case.CancelReservation;
import travelAgency.application.use_case.FlightAvailability;
import travelAgency.application.use_case.FindFind;
import travelAgency.application.use_case.FindReservationService;
import travelAgency.application.use_case.FindReservation;
import travelAgency.application.use_case.ReservationNumberGenerator;
import travelAgency.use_case.fake.FakeFlight;
import travelAgency.use_case.fake.FakePassenger;
import travelAgency.use_case.fake.FakeReservation;
import travelAgency.use_case.fake.FakeReservationNumber;

import java.time.LocalDate;

import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static travelAgency.use_case.fake.FakeFlight.flight;
import static travelAgency.use_case.fake.FakePassenger.passenger;
import static travelAgency.use_case.fake.FakeReservationInformation.reservationInformation;

public class FindReservationServiceShould {

    private static final String SARA = "Sara";
    private static final LocalDate SARA_BIRTHDAY = of(1999, 4, 5);
    private static final String EXIST_FLIGHT_NUMBER = "0321";
    public static final String NOT_FOUND_RESERVATION_NUMBER = "78456871";
    private FindReservationService app;
    private BookingReservation appService;
    private CancelReservation cancellation;

    @BeforeEach
    void setUp() {
        ReservationNumberGenerator reservationNumber = new FakeReservationNumber();
        final FakeReservation bookings = new FakeReservation();
        final FakeFlight flights = new FakeFlight();
        final FindFind flightService = new FindFind(flights);

        final FindReservationService findReservationService = new FindReservation(bookings, flightService);
        final FakePassenger passengers = new FakePassenger();
        appService = new BookingReservation(bookings, passengers,findReservationService, reservationNumber);
        app = new FindReservation(bookings, flightService);
        cancellation = new CancelReservation(bookings);
    }


    @Test
    void search_in_booking_list() {
        var ticket = app.search(EXIST_FLIGHT_NUMBER, SARA, SARA_BIRTHDAY);

        assertAll(
                () -> assertThat(ticket.flightNumber()).isEqualTo(EXIST_FLIGHT_NUMBER),
                () -> assertThat(ticket.passenger()).isEqualTo(passenger().build()),
                () -> assertThat(ticket.flight()).isEqualTo(flight().build())
        );
    }

    @Test
    void throw_NotFoundAnyBookingFlightException_when_there_is_not_any_flight_with_enter_information() {

        assertThatExceptionOfType(ReservationNotFoundException.class)
                .isThrownBy(() -> app.search("023", SARA, SARA_BIRTHDAY));
    }

    @Test
    void cancel_a_book_flight_with_ticket_number() {
        var reservation = app.search(EXIST_FLIGHT_NUMBER, SARA, SARA_BIRTHDAY);
        final int travelers = reservation.travelers();
        final int bookingsBeforeCancel = 6;
        final int bookingsAfterCancel = bookingsBeforeCancel - travelers;

        assertAll(
                () -> assertThat(app.totalBookedSeats(EXIST_FLIGHT_NUMBER)).isEqualTo(bookingsBeforeCancel),
                () -> assertThatNoException().isThrownBy(() -> cancellation.cancelReservation(reservation.reservationNumber())),
                () -> assertThat(app.totalBookedSeats(EXIST_FLIGHT_NUMBER)).isEqualTo(bookingsAfterCancel)
        );
    }

    @Test
    void a_departed_flight_can_not_be_cancelled() {
        final Reservation reservation = app.search("AA-7845-65874");
        reservation.flight().markAsDeparted();

        assertThatExceptionOfType(CouldNotCancelReservation.class)
                .isThrownBy(() -> cancellation.cancelReservation(reservation.reservationNumber()));
    }

    @Test
    void throw_exception_when_cancel_the_reservation_number_is_not_found() {
        assertThatExceptionOfType(CouldNotFoundReservation.class)
                .isThrownBy(() -> cancellation.cancelReservation(NOT_FOUND_RESERVATION_NUMBER));
    }

    @Test
    void get_number_of_booked_flight() {
        insertMultipleBooking();
        assertThat(app.totalBookedSeats("0321")).isEqualTo(19);
    }


    private void insertMultipleBooking() {
        appService.book(reservationInformation().withTravelers(5).build());
        appService.book(reservationInformation().withPassenger(passenger("4444556622")).withTravelers(1).build());
        appService.book(reservationInformation().withPassenger(passenger("5544556699")).withTravelers(3).build());
        appService.book(reservationInformation().withPassenger(passenger("2211334565")).withTravelers(4).build());
    }
}
