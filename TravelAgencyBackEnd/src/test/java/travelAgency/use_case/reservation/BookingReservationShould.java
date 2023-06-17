package travelAgency.use_case.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travelAgency.dao.database.passenger.PassengerRepository;
import travelAgency.domain.flight.Flight;
import travelAgency.domain.reservation.ReservationInformation;
import travelAgency.exceptions.FullyBookedException;
import travelAgency.exceptions.InvalidFlightNumberException;
import travelAgency.exceptions.NotEnoughCapacityException;
import travelAgency.exceptions.PastFlightScheduleException;
import travelAgency.services.BookingReservation;
import travelAgency.services.flight.FlightAvailability;
import travelAgency.services.flight.FlightListService;
import travelAgency.services.flight.FlightListServiceImpl;
import travelAgency.services.reservation.ReservationListService;
import travelAgency.services.reservation.ReservationListServiceImpl;
import travelAgency.services.reservation.ReservationNumberGenerator;
import travelAgency.use_case.fake.FakeFlight;
import travelAgency.use_case.fake.FakePassenger;
import travelAgency.use_case.fake.FakeReservationList;
import travelAgency.use_case.fake.FakeReservationNumber;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static travelAgency.domain.city.City.TEHRAN;
import static travelAgency.use_case.fake.FakeFlight.flight;
import static travelAgency.use_case.fake.FakeReservationInformation.reservationInformation;

public class BookingReservationShould {

    private BookingReservation app;

    @BeforeEach
    void setUp() {
        ReservationNumberGenerator reservationNumber = new FakeReservationNumber();
        FakeReservationList fakeBookingList = new FakeReservationList();
        FakeFlight fakeFlight = new FakeFlight();
        FlightListService flightService = new FlightListServiceImpl(fakeFlight);
        ReservationListService reservationListService
                = new ReservationListServiceImpl(fakeBookingList, flightService);
        FlightAvailability flightAvailability = new FlightAvailability(reservationListService);
        PassengerRepository passengerService = new FakePassenger();

        app = new BookingReservation(fakeBookingList, passengerService, flightAvailability, reservationNumber);
    }


    @Test
    void book_a_flight_without_throw_any_exception() {
        final ReservationInformation reservationInformation = reservationInformation().build();

        assertThatNoException().isThrownBy(() -> app.book(reservationInformation));
    }


    @Test
    void throw_InvalidFlightNumberException_when_flight_number_is_empty_or_less_than_3_length() {
        assertThatExceptionOfType(InvalidFlightNumberException.class)
                .isThrownBy(() -> app.book(reservationInformation()
                        .withFlight(flight().withFlightNumber("").build())
                        .build()));

        assertThatExceptionOfType(InvalidFlightNumberException.class)
                .isThrownBy(() -> app.book(reservationInformation()
                        .withFlight(flight().withFlightNumber("45").build())
                        .build()));
    }

    @Test
    void throw_PastFlightScheduleException_when_book_flight_with_past_departure_date() {
        assertThatExceptionOfType(PastFlightScheduleException.class)
                .isThrownBy(() -> app.book(reservationInformation()
                        .withFlight(flight("4784"))
                        .build()));
    }

    @Test
    void throw_PastFlightScheduleException_when_book_flight_with_past_arrival_date() {
        assertThatExceptionOfType(PastFlightScheduleException.class)
                .isThrownBy(() -> app.book(reservationInformation()
                        .withFlight(flight("5120"))
                        .build()));
    }

    @Test
    void throw_IllegalArgumentException_when_origin_and_destination_is_the_same() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    final Flight flight = flight().from(TEHRAN).to(TEHRAN).build();
                    app.book(reservationInformation().withFlight(flight).build());
                });
    }

    @Test
    void throw_FullyBookedException_when_attempting_to_book_a_flight_that_has_reached_full_capacity() {
        bookingAllSeats();

        assertThatExceptionOfType(FullyBookedException.class)
                .isThrownBy(() -> app.book(reservationInformation().build()));
    }

    @Test
    void throw_NotEnoughCapacityException_if_there_are_not_enough_seats_available_when_booking_a_flight() {
        bookSeats(30);

        assertThatExceptionOfType(NotEnoughCapacityException.class)
                .isThrownBy(() -> app.book(reservationInformation().withTravelers(5).build()));
    }

    private void bookingAllSeats() {
        bookSeats(34);
    }

    private void bookSeats(int numSeats) {
        // Book the specified number of seats for the initial reservation
        for (int i = 0; i < numSeats; i++) {
            app.book(reservationInformation().withTravelers(1).build());
        }
    }
}
