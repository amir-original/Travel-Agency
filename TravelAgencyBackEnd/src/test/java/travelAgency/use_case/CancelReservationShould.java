package travelAgency.use_case;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.application.use_case.CancelReservation;
import travelAgency.application.use_case.FindFlight;
import travelAgency.application.use_case.SearchReservation;
import travelAgency.application.use_case.SearchReservationService;
import travelAgency.exceptions.CouldNotCancelReservation;
import travelAgency.exceptions.CouldNotFoundReservation;
import travelAgency.model.reservation.Reservation;
import travelAgency.use_case.fake.FakeFlight;
import travelAgency.use_case.fake.FakeReservation;

import java.time.LocalDate;

import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class CancelReservationShould {

    private static final String SARA = "Sara";
    private static final LocalDate SARA_BIRTHDAY = of(1999, 4, 5);
    private static final String EXIST_FLIGHT_NUMBER = "0321";
    public static final String NOT_FOUND_RESERVATION_NUMBER = "78456871";
    private SearchReservationService app;
    private CancelReservation cancellation;

    @BeforeEach
    void setUp() {
        final FakeReservation bookings = new FakeReservation();
        final FakeFlight flights = new FakeFlight();
        final FindFlight flightService = new FindFlight(flights);

        app = new SearchReservation(bookings, flightService);
        cancellation = new CancelReservation(bookings);
    }

    @Test
    void cancel_a_booked_flight_with_ticket_number_without_throwing_any_exception() {
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
    void not_be_cancelled_a_reservation_when_a_flight_has_departed() {
        final Reservation reservation = app.search("AA-7845-65874");
        reservation.markFlightAsDeparted();

        assertThatExceptionOfType(CouldNotCancelReservation.class)
                .isThrownBy(() -> cancellation.cancelReservation(reservation.reservationNumber()));
    }

    @Test
    void not_cancel_a_reservation_when_reservation_number_is_not_found() {
        assertThatExceptionOfType(CouldNotFoundReservation.class)
                .isThrownBy(() -> cancellation.cancelReservation(NOT_FOUND_RESERVATION_NUMBER));
    }
}
