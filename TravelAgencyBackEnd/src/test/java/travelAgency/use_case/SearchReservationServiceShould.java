package travelAgency.use_case;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.model.reservation.Reservation;
import travelAgency.exceptions.CouldNotCancelReservation;
import travelAgency.exceptions.CouldNotFoundReservation;
import travelAgency.exceptions.ReservationNotFoundException;
import travelAgency.application.use_case.BookingReservation;
import travelAgency.application.use_case.CancelReservation;
import travelAgency.application.use_case.FindFlight;
import travelAgency.application.use_case.SearchReservationService;
import travelAgency.application.use_case.SearchReservation;
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

public class SearchReservationServiceShould {

    private static final String SARA = "Sara";
    private static final LocalDate SARA_BIRTHDAY = of(1999, 4, 5);
    private static final String EXIST_FLIGHT_NUMBER = "0321";
    private SearchReservationService app;

    @BeforeEach
    void setUp() {
        final FakeReservation bookings = new FakeReservation();
        final FakeFlight flights = new FakeFlight();
        final FindFlight flightService = new FindFlight(flights);

        app = new SearchReservation(bookings, flightService);
    }


    @Test
    void search_in_booking_list() {
        var reservation = app.search(EXIST_FLIGHT_NUMBER, SARA, SARA_BIRTHDAY);

        assertAll(
                () -> assertThat(reservation.flightNumber()).isEqualTo(EXIST_FLIGHT_NUMBER),
                () -> assertThat(reservation.passenger()).isEqualTo(passenger().build())
        );
    }

    @Test
    void throw_exception_when_there_is_not_any_flight_with_enter_information() {

        assertThatExceptionOfType(ReservationNotFoundException.class)
                .isThrownBy(() -> app.search("023", SARA, SARA_BIRTHDAY));
    }
}
